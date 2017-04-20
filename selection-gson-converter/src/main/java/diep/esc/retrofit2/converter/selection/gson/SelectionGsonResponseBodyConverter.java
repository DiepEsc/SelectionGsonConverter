package diep.esc.retrofit2.converter.selection.gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by diep on 4/13/17.
 */

class SelectionGsonResponseBodyConverter<T extends SelectedData, X extends BaseResponseMessage>
        implements Converter<ResponseBody, T> {
    private Converter<ResponseBody, X> convert;

    public SelectionGsonResponseBodyConverter(Converter<ResponseBody, X> convert) {
        this.convert = convert;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Object converted = convert.convert(value);
        if (!BaseResponseMessage.class.isAssignableFrom(converted.getClass())) {
            throw new IllegalArgumentException(converted.getClass().getCanonicalName() +
                    " must be implement" +
                    " the interface " + SelectedData.class.getCanonicalName());
        }
        X allData = (X) converted;
        return (T) allData.getSelectedData();
    }
}