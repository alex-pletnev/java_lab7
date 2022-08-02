public class Turtle <T extends Number> {


    public T leftValue;
    public T rightValue;

    public Turtle(T leftValue, T rightValue) {
        // again, T is being used as a placeholder for any type
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }
}