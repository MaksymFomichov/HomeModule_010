package company.shop;

public class Client {
    private String name;
    private int count;
    private Fruit.TypeFruit typeFruit;

    public Client(String name, int count, Fruit.TypeFruit typeFruit) {
        this.name = name;
        this.count = count;
        this.typeFruit = typeFruit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Fruit.TypeFruit getTypeFruit() {
        return typeFruit;
    }

    public void setTypeFruit(Fruit.TypeFruit typeFruit) {
        this.typeFruit = typeFruit;
    }
}
