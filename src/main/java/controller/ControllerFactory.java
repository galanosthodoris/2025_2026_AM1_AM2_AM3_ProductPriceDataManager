package controller;

/**
 * Factory class to obtain instances of IController.
 * <p>
 * Clients should only use this factory and never import concrete implementations of IController directly.
 * By providing a ControllerFactory, front-end code only depends on the interface IController. 
 * Concrete classes are hidden. 
 * This supports decoupling, mocking for tests, and future replacement of the implementation without touching clients.
 * <p>
 * Observe the static method: this just simplifies the code, as there is no need to instantiate ControllerFactory
 * via a new object.
 * <p>
 * Also observe the private constructor: you will learn this trick later, in the Singleton pattern, 
 * still, the main idea is to prevent creating objects for this kind of classes with static calls.
 */
public final class ControllerFactory {

    // Private constructor to prevent instantiation
    private ControllerFactory() { }

    /**
     * Creates a new instance of IController.
     *
     * @return a concrete IController implementation
     */
    public static IController createController() {
        return new MyController(); //with new WhateverYourClassIs();
    }

}
