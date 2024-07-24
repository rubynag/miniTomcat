package com.minit;

import java.util.EventObject;

public final class ContainerEvent extends EventObject {
    private Container container = null;

    private Object data = null;

    private String type = null;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContainerEvent(Container source,String type,Object data) {
        super(source);
        this.container = source;
        this.type = type;
        this.data = data;
    }

    public Object getData() {
        return (this.data);
    }

    public Container getContainer() {
        return (this.container);
    }

    public String getType() {
        return (this.type);
    }

    public String toString() {
        return ("ContainerEvent['" + getContainer() + "','" +
                getType() + "','" + getData() + "']");
    }
}
