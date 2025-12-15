package villagedevs.statemachine;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import villagedevs.statemachine.annotation.StateHandlerState;
import villagedevs.statemachine.annotation.StateServiceJob;
import villagedevs.statemachine.initializer.StateMachineInitializer;
import villagedevs.statemachine.job.Job;
import villagedevs.statemachine.state.State;
import villagedevs.statemachine.state.StateHandler;
import villagedevs.statemachine.state.StateTransition;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * State machine initializer using annotations
 */
public class StateMachineAnnotationInitializer implements StateMachineInitializer {

    @Override
    public StateMachineManager createNewManager() {

        //TODO: packet name into env var
        Reflections reflections = new Reflections("villagedevs", new SubTypesScanner(false), new TypeAnnotationsScanner());


        return new StateMachineManager(initStateServices(reflections));
    }

    private <T extends Enum<T> & State> T getState(StateHandler stateHandler, Class<T> stateType) {

        StateHandlerState annotation = stateHandler.getClass().getAnnotation(StateHandlerState.class);
        String stateName = annotation.value();

        return Enum.valueOf(stateType, stateName);

    }

    private <T extends Enum<T> & State> Class<T> getStateType(Class<?>[] typeArgs) {
        return (Class<T>) typeArgs[1];
    }

    private <T extends Enum<T> & State> Map<Class<? extends Job>, StateTransition> initStateServices(Reflections reflections) {

        Set<Class<?>> allStateServices = reflections.getTypesAnnotatedWith(StateServiceJob.class);
        Map<Class<? extends Job>, StateTransition> stateTransitionMap = new HashMap<>();

        for (Class classService : allStateServices) {
            Set<Class<?>> allStateHandlers = reflections.getTypesAnnotatedWith(StateHandlerState.class);


            Class<?>[] typeArgs = extractHandlerTypeArguments(classService);
            Class<? extends Job> jobType = (Class<? extends Job>) typeArgs[0];   // например, Task
            Class<T> stateType = getStateType(typeArgs);

            Map<State, StateHandler> stateHandlerList = allStateHandlers.stream()
                    .filter(shc -> stateHandlerFilter(shc, jobType, stateType))
                    .map(this::createNewInstance)
                    .map(StateHandler.class::cast)
                    .collect(Collectors.toMap(handler -> getState(handler, stateType), h -> h));


            stateTransitionMap.put(jobType, (StateTransition) createNewInstance(classService, stateHandlerList));
        }

        return stateTransitionMap;

    }

    private Class<?>[] extractHandlerTypeArguments(Class<?> handlerClass) {
        Type type = handlerClass.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            Type[] typeArguments = pType.getActualTypeArguments();
            if (typeArguments.length == 2) {
                return new Class[]{resolveType(typeArguments[0]), resolveType(typeArguments[1])};
            }
        }
        throw new IllegalArgumentException("Cannot determine type arguments for StateHandler in " + handlerClass.getName());
    }

    private Class<?> resolveType(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
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

    private Object createNewInstance(Class clazz, Map handlerLi) {
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
            return constructor.newInstance(handlerLi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
