package diep.esc.retrofit2.converter.selection.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by diep on 4/13/17.
 */

public class SelectionGsonConverterFactory extends Converter.Factory {
    private GsonConverterFactory requestBodyConverterFactory;
    private Class baseResponseClass;
    private Gson defaultGson;

    public SelectionGsonConverterFactory(Class baseResponseClass) {
        defaultGson = new Gson();
        this.requestBodyConverterFactory = GsonConverterFactory.create(defaultGson);
        this.baseResponseClass = baseResponseClass;
    }

    @Override
    public Converter<ResponseBody, ? extends SelectedData> responseBodyConverter(Type type, Annotation[] annotations,
                                                                                 Retrofit retrofit) {
        if (!(type instanceof Class)) {
            throw new IllegalArgumentException("type must be an instance of java.lang.Class." +
                    " But it is " + type);
        }
        Class clazz = (Class) type;
        if (!SelectedData.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz.getCanonicalName() + " must be implement" +
                    " the interface " + SelectedData.class.getCanonicalName());
        }

        SelectedDataDeserializer adapter = new SelectedDataDeserializer(defaultGson, type);
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(SelectedData.class, adapter);
        GsonConverterFactory factory = GsonConverterFactory.create(builder.create());

        return new SelectionGsonResponseBodyConverter(factory.responseBodyConverter(baseResponseClass, annotations, retrofit));
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations, Retrofit retrofit) {

        return requestBodyConverterFactory.requestBodyConverter(type, parameterAnnotations,
                methodAnnotations, retrofit);
    }
}