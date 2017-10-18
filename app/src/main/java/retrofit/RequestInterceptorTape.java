package retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit.RequestInterceptor.RequestFacade;

final class RequestInterceptorTape implements RequestFacade, RequestInterceptor {
    private final List<CommandWithParams> tape;

    private enum Command {
        ADD_HEADER {
            public void intercept(RequestFacade facade, String name, String value) {
                facade.addHeader(name, value);
            }
        },
        ADD_PATH_PARAM {
            public void intercept(RequestFacade facade, String name, String value) {
                facade.addPathParam(name, value);
            }
        },
        ADD_ENCODED_PATH_PARAM {
            public void intercept(RequestFacade facade, String name, String value) {
                facade.addEncodedPathParam(name, value);
            }
        },
        ADD_QUERY_PARAM {
            public void intercept(RequestFacade facade, String name, String value) {
                facade.addQueryParam(name, value);
            }
        },
        ADD_ENCODED_QUERY_PARAM {
            public void intercept(RequestFacade facade, String name, String value) {
                facade.addEncodedQueryParam(name, value);
            }
        };

        abstract void intercept(RequestFacade requestFacade, String str, String str2);
    }

    private static final class CommandWithParams {
        final Command command;
        final String name;
        final String value;

        CommandWithParams(Command command, String name, String value) {
            this.command = command;
            this.name = name;
            this.value = value;
        }
    }

    RequestInterceptorTape() {
        this.tape = new ArrayList();
    }

    public void addHeader(String name, String value) {
        this.tape.add(new CommandWithParams(Command.ADD_HEADER, name, value));
    }

    public void addPathParam(String name, String value) {
        this.tape.add(new CommandWithParams(Command.ADD_PATH_PARAM, name, value));
    }

    public void addEncodedPathParam(String name, String value) {
        this.tape.add(new CommandWithParams(Command.ADD_ENCODED_PATH_PARAM, name, value));
    }

    public void addQueryParam(String name, String value) {
        this.tape.add(new CommandWithParams(Command.ADD_QUERY_PARAM, name, value));
    }

    public void addEncodedQueryParam(String name, String value) {
        this.tape.add(new CommandWithParams(Command.ADD_ENCODED_QUERY_PARAM, name, value));
    }

    public void intercept(RequestFacade request) {
        for (CommandWithParams cwp : this.tape) {
            cwp.command.intercept(request, cwp.name, cwp.value);
        }
    }
}
