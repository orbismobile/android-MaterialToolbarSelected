package com.orbismobile.materialtoolbarselected;

/**
 * Created by carlosleonardocamilovargashuaman on 8/14/17.
 */

public class UserEntity {

    private String name;
    private boolean selected;

    public UserEntity(String name, boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
