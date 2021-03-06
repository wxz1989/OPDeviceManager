package retrofit.mime;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

public final class FormUrlEncodedTypedOutput implements TypedOutput {
    final ByteArrayOutputStream content = new ByteArrayOutputStream();

    public void addField(String str, String str2) {
        addField(str, true, str2, true);
    }

    public void addField(String str, boolean z, String str2, boolean z2) {
        if (str == null) {
            throw new NullPointerException("name");
        } else if (str2 != null) {
            if (this.content.size() > 0) {
                this.content.write(38);
            }
            if (z) {
                str = URLEncoder.encode(str, "UTF-8");
            }
            if (z2) {
                str2 = URLEncoder.encode(str2, "UTF-8");
            }
            try {
                this.content.write(str.getBytes("UTF-8"));
                this.content.write(61);
                this.content.write(str2.getBytes("UTF-8"));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new NullPointerException("value");
        }
    }

    public String fileName() {
        return null;
    }

    public long length() {
        return (long) this.content.size();
    }

    public String mimeType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    public void writeTo(OutputStream outputStream) {
        outputStream.write(this.content.toByteArray());
    }
}
