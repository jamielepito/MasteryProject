package learn.mastery.domain;

// 'T' Allow for flexibility in type
public class Result<T> extends Response {

    private T payload;

    public Result(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}
