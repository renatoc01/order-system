import java.util.ArrayList;
import java.util.List;

public class Company implements ICompany {

    // List of registered users in the company
    private List<IUser> users = new ArrayList<>();

    // List of products in inventory (each Node = product + quantity)
    private List<Node> products = new ArrayList<>();

    @Override
    public List<IUser> getUsers() {
        // Return the list of users
        return users;
    }

    @Override
    public void setUsers(List<IUser> users) {
        // Replace the current list of users with the provided list
        this.users = users;
    }

    @Override
    public List<Node> getProducts() {
        // Return the list of products in inventory
        return products;
    }

    @Override
    public void setProducts(List<Node> products) {
        // Set a new list of products for the company
        this.products = products;
    }

    @Override
    public void addUser(IUser user) {
        // Add a new user to the users list
        users.add(user);
    }

    @Override
    public void addProduct(IProduct product, int quantity) {

        // Iterate through the existing products in inventory
        for (Node node : products) {

            // Check if a product with the same ID already exists
            if (node.product.getId() == product.getId()) {

                // If it exists, increase the available quantity
                node.quantity += quantity;

                // Exit the method since the product is already updated
                return;
            }
        }

        // If the product does not exist in the inventory,
        // create a new Node with the product and its quantity
        products.add(new Node(product, quantity));
    }

    @Override
    public void makeOrder(List<Node> orderProducts, IUser user) {

        // Stores the highest shipping cost among ordered products
        double highestShipping = 0;

        // Stores the total order cost
        double totalCost = 0;

        // -------- 1️⃣ Check product availability --------
        for (Node order : orderProducts) {

            // Find the product in the inventory using its ID
            Node stock = findProduct(order.product.getId());

            // If the product doesn't exist or quantity is insufficient
            if (stock == null || stock.quantity < order.quantity) {

                // Cancel the order
                return;
            }
        }

        // -------- 2️⃣ Calculate total cost --------
        for (Node order : orderProducts) {

            // Add product price multiplied by quantity to the total cost
            totalCost += order.product.getPrice() * order.quantity;

            // Update the highest shipping cost among the ordered products
            highestShipping = Math.max(
                    highestShipping,
                    order.product.getShippingCost());
        }

        // Add the highest shipping cost to the total order price
        totalCost += highestShipping;

        // -------- 3️⃣ Verify user's balance --------
        if (user.getBalance() < totalCost) {

            // Cancel order if the user does not have enough funds
            return;
        }

        // -------- 4️⃣ Update user's balance --------
        // Deduct the order cost from the user's balance
        ((User) user).setBalance(user.getBalance() - totalCost);

        // -------- 5️⃣ Update product quantities in inventory --------
        for (Node order : orderProducts) {

            // Find the product in inventory
            Node stock = findProduct(order.product.getId());

            // Reduce the stock quantity by the ordered amount
            stock.quantity -= order.quantity;
        }

        // -------- 6️⃣ Add the order to the user's order history --------
        user.getOrders().addAll(orderProducts);
    }

    // Helper method to find a product in the inventory by its ID
    private Node findProduct(int productId) {

        // Iterate through all products in inventory
        for (Node node : products) {

            // If the product ID matches
            if (node.product.getId() == productId) {

                // Return the corresponding Node
                return node;
            }
        }

        // Return null if the product is not found
        return null;
    }
}
