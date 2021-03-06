package retrofit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

final class Utils {
    private static final int BUFFER_SIZE = 4096;

    class SynchronousExecutor implements Executor {
        SynchronousExecutor() {
        }

        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    private Utils() {
    }

    static Request readBodyToBytesIfNecessary(Request request) {
        TypedOutput body = request.getBody();
        if (body == null || (body instanceof TypedByteArray)) {
            return request;
        }
        String mimeType = body.mimeType();
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        body.writeTo(byteArrayOutputStream);
        return new Request(request.getMethod(), request.getUrl(), request.getHeaders(), new TypedByteArray(mimeType, byteArrayOutputStream.toByteArray()));
    }

    static Response readBodyToBytesIfNecessary(Response response) {
        TypedInput body = response.getBody();
        if (body == null || (body instanceof TypedByteArray)) {
            return response;
        }
        String mimeType = body.mimeType();
        InputStream in = body.in();
        try {
            Response replaceResponseBody = replaceResponseBody(response, new TypedByteArray(mimeType, streamToBytes(in)));
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            return replaceResponseBody;
        } catch (Throwable th) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {
                }
            }
        }
    }

    static Response replaceResponseBody(Response response, TypedInput typedInput) {
        return new Response(response.getUrl(), response.getStatus(), response.getReason(), response.getHeaders(), typedInput);
    }

    static byte[] streamToBytes(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (inputStream != null) {
            byte[] bArr = new byte[BUFFER_SIZE];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    static void validateServiceClass(Class cls) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException("Only interface endpoint definitions are supported.");
        } else if (cls.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Interface definitions must not extend other interfaces.");
        }
    }
}
