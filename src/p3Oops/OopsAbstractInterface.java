// Abstract classes are similar to interfaces. You cannot instantiate them, and they may contain a mix of methods declared with or without an implementation. However, with abstract classes, you can declare fields that are not static and final, and define public, protected, and private concrete methods. With interfaces, all fields are automatically public, static, and final, and all methods that you declare or define (as default methods) are public and abstract. In addition, you can extend only one class, whether or not it is abstract, whereas you can implement any number of interfaces.

// Which should you use,abstract classes or interfaces?

// Consider using abstract classes if any of these statements apply to your situation:
// 1. You want to share code among several closely related classes.
// 2. You expect that classes that extend your abstract class have many common methods or fields, or require access modifiers other than public(such as protected and private).
// 3. You want to declare non-static or non-final fields. This enables you to define methods that can access and modify the state of the object to which they belong.

// Consider using interfaces if any of these statements apply to your situation:
// 1. You expect that unrelated classes would implement your interface. For example, the interfaces Comparable and Cloneable are implemented by many unrelated classes.
// 2. You want to specify the behavior of a particular data type, but not concerned about who implements its behavior.
// 3. You want to take advantage of multiple inheritance of type.

package p3Oops;

abstract class Shape {
    private final int dim1;
    private final int dim2;

    public Shape(int dim1, int dim2) {
        this.dim1 = dim1;
        this.dim2 = dim2;
    }

    public int getDim1() {
        return dim1;
    }

    public int getDim2() {
        return dim2;
    }

    abstract public void areaOfShape();

    abstract public void perimeterOfShape();
}

class Square extends Shape {
    public Square(int dim) {
        super(dim, -1);
    }

    @Override
    public void areaOfShape() {
        int area = this.getDim1() * this.getDim1();
        System.out.println(area);
    }

    @Override
    public void perimeterOfShape() {
        int perimeter = 2 * (this.getDim1() + this.getDim1());
        System.out.println(perimeter);
    }
}

class Rectangle extends Shape {
    public Rectangle(int dim1, int dim2) {
        super(dim1, dim2);
    }

    @Override
    public void areaOfShape() {
        int area = this.getDim1() * this.getDim2();
        System.out.println(area);
    }

    @Override
    public void perimeterOfShape() {
        int perimeter = 2 * (this.getDim1() + this.getDim2());
        System.out.println(perimeter);
    }
}

class Circle extends Shape {
    public Circle(int dim1) {
        super(dim1, -1);
    }

    @Override
    public void areaOfShape() {
        double area = Math.PI * this.getDim1() * this.getDim1();
        System.out.println(area);
    }

    @Override
    public void perimeterOfShape() {
        double perimeter = 2 * Math.PI * this.getDim1();
        System.out.println(perimeter);
    }
}

interface Bicycle {
    void applyBrakes(int decrement);

    void speedUp(int increment);

    private void startBicycle() {
        System.out.println("Starting Bicycle at Gear " + 1);
    }

    default void changeGear(int finalGear) {
        this.startBicycle();
        System.out.println("Changing Gear to " + finalGear);
    }
}

interface Lights {
    void frontLight();

    void backLight();
}

interface Horns {
    void hindiSong();

    void englishSong();
}

record MyCycle(int speed) implements Bicycle, Lights, Horns {

    public void applyBrakes(int decrement) {
        System.out.println(this.speed() - decrement);
    }

    public void speedUp(int increment) {
        System.out.println(this.speed() + increment);
    }

    public void frontLight() {
        System.out.println("Turning Front Light On...");
    }

    public void backLight() {
        System.out.println("Turning Back Light On...");
    }

    public void hindiSong() {
        System.out.println("Blowing Hindi Song Horn...");
    }

    public void englishSong() {
        System.out.println("Blowing English Song Horn...");
    }
}

public class OopsAbstractInterface {
    public static void main(String[] args) {
        /*
         * Square S = new Square(5);
         * S.areaOfShape();
         * S.perimeterOfShape();
         * 
         * System.out.println();
         * 
         * Rectangle R = new Rectangle(5,6);
         * R.areaOfShape();
         * R.perimeterOfShape();
         * 
         * System.out.println();
         * 
         * Circle C = new Circle(5);
         * C.areaOfShape();
         * C.perimeterOfShape();
         */

        // Run Time Polymorphism (using Interfaces)
        /*
         * Bicycle MCB = new MyCycle(5); // Only functions of Bicycle will be allowed in
         * MCB object
         * Lights MCL = new MyCycle(5); // Only functions of Lights will be allowed in
         * MCB object
         * Horns MCH = new MyCycle(5); // Only functions of Horns will be allowed in MCB
         * object
         * MCB.applyBrakes(2);
         * MCB.speedUp(3);
         * MCL.frontLight();
         * MCH.hindiSong();
         * MCL.backLight();
         * MCH.englishSong();
         * MCB.changeGear(3);
         */

        System.out.println("Hello!");
    }
}