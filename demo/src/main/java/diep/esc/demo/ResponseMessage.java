package diep.esc.demo;

import diep.esc.retrofit2.converter.selection.gson.BaseResponseMessage;
import diep.esc.retrofit2.converter.selection.gson.SelectedData;

/**
 * Created by diep on 4/20/17.
 */

public class ResponseMessage implements BaseResponseMessage {
    @Override
    public SelectedData getSelectedData() {
        return data;
    }
    private String author;
    private String github;
    private SelectedMessageData data;
}
