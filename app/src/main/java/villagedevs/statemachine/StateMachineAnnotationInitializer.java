package villagedevs.statemachine;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateHandler;
import villagedevs.statemachine.state.StateTransition;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StateMachineAnnotationInitializer {

    public StateMachineManager createNewManager() {

        //packet name into env var
        Reflections reflections = new Reflections("villagedevs", new SubTypesScanner(false), new TypeAnnotationsScanner());


        return new StateMachineManager(initStateServices(reflections));
    }

    private Map<Class<? extends Job>, StateTransition> initStateServices(Reflections reflections) {
        Set<Class<? extends Job>> allJobs = reflections.getSubTypesOf(Job.class);
        Set<Class<?>> allStateServices = reflections.getTypesAnnotatedWith(StateServiceJob.class);

        for (Class<? extends Job> jobType : allJobs) {
            List<StateTransition> stateTransitionServices = allStateServices.stream()
                    .filter(stateServiceClass -> stateTransitionServicesFilter(stateServiceClass, jobType))
                    .map(this::createNewInstance)
                    .map(StateTransition.class::cast)
                    .toList();

            if (stateTransitionServices.size() != 1) {
                continue;
                //exception
            } else {
                var stateService = stateTransitionServices.get(0);

                initStateHandlers(reflections, stateService);
            }
        }

        return null;
    }

    private boolean stateTransitionServicesFilter(Class<?> stateServiceClass, Class<? extends Job> jobType) {

        if (stateServiceClass.getGenericInterfaces().length != 1) {
            //exception
        }

        //ParameterizedType paramGenericInterface = (ParameterizedType) stateServiceClass.getGenericInterfaces()[0];
        Type[] genericInterfaces = stateServiceClass.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            Type[] genericInterface = ((ParameterizedType) stateServiceClass.getGenericInterfaces()[0]).getActualTypeArguments();
            if (genericInterface.length != 2) {
                //exception
            }
            Type stateServiceJobType = genericInterface[0];
            return stateServiceJobType.equals(jobType);
        }


        Type[] genericSuperClass = ((ParameterizedType) stateServiceClass.getGenericSuperclass()).getActualTypeArguments();
        if (genericSuperClass.length != 2) {
            //exception
        }
        Type stateServiceJobType = genericSuperClass[0];
        return stateServiceJobType.equals(jobType);
    }

    private void initStateHandlers(Reflections reflections, StateTransition stateTransitionService) {
        Set<Class<? extends State>> allStates = reflections.getSubTypesOf(State.class);
        Set<Class<?>> allStateHandlers = reflections.getTypesAnnotatedWith(StateHandlerState.class);

        if (stateTransitionService.getClass().getGenericInterfaces().length != 1) {
            //exception?
        }

        Type[] actualTypeArguments = ((ParameterizedType) stateTransitionService.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
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
                        .filter(shc -> stateHandlerFilter(shc, jobType, stateType))
                        .map(this::createNewInstance)
                        .map(StateHandler.class::cast);
                        //if the handler has not been created, then we swallow it - we need to check and throw the error because then the npe is in progress.
                      //  .forEach(h -> stateTransitionService.addHandler(state, h));
            }
        }
    }

    private boolean stateHandlerFilter(Class<?> shc, Type jobType, Type stateType) {
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
    }

    //WIP
    private Object createNewInstance(Class clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor constructor = null;

        for (int i = 0; i < constructors.length; i++) {
            constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0)
                break;
        }

        if (constructor == null) {
            //do something
        }

        try {
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
