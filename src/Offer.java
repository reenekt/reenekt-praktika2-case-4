import java.util.List;

public class Offer {
    private int id;
    private String productName;
    private String vendorCode;
    private String categoryCode;
    private float pricePerUnit;
    private int quantity;

    // region getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public float getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(float pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    // endregion getters and setters

    public Offer(
        int id,
        String productName,
        String vendorCode,
        String categoryCode,
        float pricePerUnit,
        int quantity
    ) {
        this.setId(id);
        this.setProductName(productName);
        this.setVendorCode(vendorCode);
        this.setCategoryCode(categoryCode);
        this.setPricePerUnit(pricePerUnit);
        this.setQuantity(quantity);
    }

    public static Offer fromValuesString(String valuesString) {
//        String[] values = valuesString.split("/(\\s|\\d\\.)*,(\\s|\\d\\.)*/");
        String[] values = valuesString.split(",");

        int id = Integer.parseInt(values[0]);
        String productName = values[1];
        String vendorCode = values[2];
        String categoryCode = values[3];
        float pricePerUnit = Float.parseFloat(values[4]);
        int quantity = Integer.parseInt(values[5]);

        return new Offer(
            id,
            productName,
            vendorCode,
            categoryCode,
            pricePerUnit,
            quantity
        );
    }

    public String toValuesString() {
        List<String> values = List.of(
                String.valueOf(getId()),
                getProductName(),
                getVendorCode(),
                getCategoryCode(),
                String.valueOf(getPricePerUnit()),
                String.valueOf(getQuantity())
        );

        return String.join(",", values);
    }

    @Override
    public String toString() {
        return "Offer {" + this.toValuesString() + "}";
    }
}
