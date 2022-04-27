package gui.client.requests;

import java.io.Serializable;

public interface GenericRequest extends Serializable {
    String getType();
}
