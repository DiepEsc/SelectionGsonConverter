package diep.esc.retrofit2.converter.selection.gson;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by diep on 4/13/17.
 */

class SelectedDataDeserializer implements JsonDeserializer<SelectedData> {
    private Type type;
    private Gson gson;

    public SelectedDataDeserializer(Gson gson, Type type) {
        this.type = type;
        this.gson = gson;
    }

    @Override
    public SelectedData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json == null) return null;
        SelectedData data = gson.fromJson(json, type);
        return data;
    }
}
