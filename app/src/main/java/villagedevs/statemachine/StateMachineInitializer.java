package villagedevs.statemachine;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.interfaces.Job;
import villagedevs.statemachine.interfaces.State;
import villagedevs.statemachine.interfaces.StateHandler;
import villagedevs.statemachine.interfaces.StateService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class StateMachineInitializer {

    public StateMachineManager createNewManager() {

        final StateMachineManager stateMachineManager = new StateMachineManager();
        //packet name into env var
        Reflections reflections = new Reflections("villagedevs", new SubTypesScanner(false), new TypeAnnotationsScanner());
        initStateServices(reflections, stateMachineManager);

        return stateMachineManager;
    }

    private void initStateServices(Reflections reflections, StateMachineManager stateMachineManager) {
        Set<Class<? extends Job>> allJobs = reflections.getSubTypesOf(Job.class);
        Set<Class<?>> allStateServices = reflections.getTypesAnnotatedWith(StateServiceJob.class);

        for (Class<? extends Job> jobType : allJobs) {
            List<StateService> stateServices = allStateServices.stream()
                    .filter(stateServiceClass -> {
                        if (stateServiceClass.getGenericInterfaces().length != 1) {
                            //exception
                        }

                        Type[] genericInterface = ((ParameterizedType) stateServiceClass.getGenericInterfaces()[0]).getActualTypeArguments();

                        if (genericInterface.length != 2) {
                            //exception
                        }

                        Type stateServiceJobType = genericInterface[0];
                        return stateServiceJobType.equals(jobType);

                    })
                    .map(clazz -> createNewInstance(clazz))
                    .map(StateService.class::cast)
                    .toList();

            if (stateServices.size() != 1) {
                continue;
                //exception
            } else {
                var stateService = stateServices.get(0);

                initStateHandlers(reflections, stateService);

                stateMachineManager.addStateService(jobType, stateService);
            }
        }
    }

    private void initStateHandlers(Reflections reflections, StateService stateService) {
        Set<Class<? extends State>> allStates = reflections.getSubTypesOf(State.class);
        Set<Class<?>> allStateHandlers = reflections.getTypesAnnotatedWith(StateHandlerState.class);

        if (stateService.getClass().getGenericInterfaces().length != 1) {
            //exception?
        }

        Type[] actualTypeArguments = ((ParameterizedType) stateService.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        if (actualTypeArguments.length != 2) {
            //exception
        }

        final Type jobType = actualTypeArguments[0];
        final Type stateType = actualTypeArguments[1];

        for (Class<? extends State> stateClass : allStates) {
            if (!stateClass.getTypeName().equals(stateType.getTypeName())) {
                continue;
            }
            for (State state : stateClass.getEnumConstants()) {
                //check ALL handlers for each enum constant, even if types are not the same... fix that
                allStateHandlers.stream()
                        .filter(shc -> {
                            if (shc.getGenericInterfaces().length != 1) {
                                //exception
                            }

                            Type[] actualStateHandlerClassTypeArguments = ((ParameterizedType) shc.getGenericInterfaces()[0]).getActualTypeArguments();
                            if (actualStateHandlerClassTypeArguments.length != 2) {
                                //exception
                            }

                            Type stateHandlerJobType = actualStateHandlerClassTypeArguments[0];
                            Type stateHandlerStateType = actualStateHandlerClassTypeArguments[1];

                            return jobType.equals(stateHandlerJobType) && stateType.equals(stateHandlerStateType);
                        })
                        .map(this::createNewInstance)
                        .map(StateHandler.class::cast)
                        //if the handler has not been created, then we swallow it - we need to check and throw the error because then the npe is in progress.
                        .forEach(h -> stateService.addHandler(state, h));
            }
        }
    }

    //WIP
    private Object createNewInstance(Class clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor ctor = null;

        for (int i = 0; i < constructors.length; i++) {
            ctor = constructors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }

        if (ctor == null) {
            //do something
        }

        try {
            return ctor.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }
}
