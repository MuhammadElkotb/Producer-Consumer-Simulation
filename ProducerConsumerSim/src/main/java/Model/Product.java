package Model;

public class Product {
    private String color = "black";


    public Product(){
        this.color = ColorGenerator.generate();
    }
    public Product copy(){
        Product newProduct = new Product();
        newProduct.color = this.color;
        return newProduct;
    }
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString(){
        return this.color.substring(4, 6);
    }
}
