package argustags.argustags_phase_ii.util;

public enum ResultMessage {
    SUCCESS("success"),
    FAILED(
            "failed"),
    REPEATEDNAME("already exist"),
    CREDITINSUFFICIENT("credit not enough"),
    LIMITEDWORKERS("surplus workers");
    public String str;
    private ResultMessage(String s){
        this.str=s;
    }
    @Override
    public String toString() {
        return str;
    }
}
