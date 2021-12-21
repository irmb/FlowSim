package irmb.flowsim.presentation.factory;

import irmb.flowsim.model.*;

/**
 * Created by Sven on 14.12.2016.
 */
public class ShapeFactoryImpl implements ShapeFactory {
    @Override
    public Shape makeShape(String type) {
        // TODO
        return switch (type) {
            default -> null;
        };
    }
}
