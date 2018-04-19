package assignment2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallResult {

@SerializedName("Success")
@Expose
private Boolean success;
@SerializedName("Message")
@Expose
private Object message;
@SerializedName("Data")
@Expose
private List<coinInfo> data = null;

/**
* No args constructor for use in serialization
*
*/
public CallResult() {
}

/**
*
* @param message
* @param data
* @param success
*/
public CallResult(Boolean success, Object message, List<coinInfo> data) {
super();
this.success = success;
this.message = message;
this.data = data;
}

public Boolean getSuccess() {
return success;
}

public void setSuccess(Boolean success) {
this.success = success;
}

public Object getMessage() {
return message;
}

public void setMessage(Object message) {
this.message = message;
}

public List<coinInfo> getData() {
return data;
}

public void setData(List<coinInfo> data) {
this.data = data;
}

}