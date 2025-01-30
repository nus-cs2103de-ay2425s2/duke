package io;

public class ValidationToken {

    private boolean isValid;
    private InputError errorMessage;

    public enum InputError {
        LIST_TOO_MANY_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "list expects 0 argument but received more than 0 arguments";
            }
        },
        BYE_TOO_MANY_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "bye expects 0 argument but received more than 0 arguments";
            }
        },
        MARK_TOO_MANY_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "mark expects 1 argument but received more than 1 argument";
            }
        },
        MARK_INCORRECT_ARGUMENT() {
            @Override
            public String getErrorMessage() {
                return "mark expects 1 argument that is an integer but received an non-integer argument";
            }
        },
        MARK_TOO_LITTLE_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "mark expects 1 argument but received 0 arguments";
            }
        },
        UNMARK_TOO_MANY_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "unmark expects 1 argument but received more than 1 argument";
            }
        },
        UNMARK_INCORRECT_ARGUMENT() {
            @Override
            public String getErrorMessage() {
                return "unmark expects 1 argument that is an integer but received an non-integer argument";
            }
        },
        UNMARK_TOO_LITTLE_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "unmark expects 1 argument but received 0 arguments";
            }
        },
        TODO_TOO_LITTLE_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "todo expects at least 1 argument but received 0 arguments";
            }
        },
        DEADLINE_TOO_LITTLE_ARGUMENTS() {
            @Override
            public String getErrorMessage() {
                return "deadline expects at least 2 arguments";
            }
        },
        DEADLINE_MISSING_BY {
            @Override
            public String getErrorMessage() {
                return "deadline expects '/by' as one of its arguments";
            }
        },
        DEADLINE_MISSING_DEADLINE {
            @Override
            public String getErrorMessage() {
                return "deadline expects at least 1 argument after '/by' but received none";
            }
        },
        DEADLINE_INVALID_DATETIME {
            @Override
            public String getErrorMessage() {
                return "deadline expects a datetime argument in the format dd/mm or dd/MM HH:mm or Mon " +
                        "but received something different";
            }
        },
        EVENT_MISSING_FROM_TO_ARGUMENTS {
            public String getErrorMessage() {
                return "event expects both /from and /to as arguments but did not receive it";
            }
        },
        EVENT_INVALID_DATETIME {
            @Override
            public String getErrorMessage() {
                return "event expects a datetime argument in the format dd/mm or dd/MM HH:mm or Mon " +
                        "after /from and /to but received something different";
            }
        },
        INVALID_COMMAND {
            public String getErrorMessage() {
                return "command listed does not exists. use todo, mark, unmark, deadline and event instead";
            }
        };

        public abstract String getErrorMessage();
    }

    public ValidationToken(boolean isValid) {
        this.isValid = isValid;
    }

    public ValidationToken(boolean isValid, InputError errorMessage) {
        this.isValid = isValid;
        this.errorMessage = errorMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage.getErrorMessage();
    }
}
