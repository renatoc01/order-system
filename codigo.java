import java.util.ArrayList;
import java.util.List;

public class Company implements ICompany {

    // Lista de usuários cadastrados na empresa
    private List<IUser> users = new ArrayList<>();

    // Lista de produtos em estoque (cada Node = produto + quantidade)
    private List<Node> products = new ArrayList<>();

    @Override
    public List<IUser> getUsers() {
        // Retorna a lista de usuários cadastrados
        return users;
    }

    @Override
    public void setUsers(List<IUser> users) {
        // Substitui a lista atual de usuários pela lista recebida
        this.users = users;
    }

    @Override
    public List<Node> getProducts() {
        // Retorna a lista de produtos em estoque
        return products;
    }

    @Override
    public void setProducts(List<Node> products) {
        // Define uma nova lista de produtos (estoque)
        this.products = products;
    }

    @Override
    public void addUser(IUser user) {
        // Adiciona um novo usuário na lista de usuários
        users.add(user);
    }

    @Override
    public void addProduct(IProduct product, int quantity) {

        // Percorre todos os produtos já cadastrados no estoque
        for (Node node : products) {

            // Verifica se já existe um produto com o mesmo ID
            if (node.product.getId() == product.getId()) {

                // Se existir, apenas soma a quantidade ao estoque existente
                node.quantity += quantity;

                // Sai do método pois o produto já foi atualizado
                return;
            }
        }

        // Se não encontrou o produto na lista, cria um novo Node
        // Node guarda o produto e a quantidade em estoque
        products.add(new Node(product, quantity));
    }

    @Override
    public void makeOrder(List<Node> orderProducts, IUser user) {

        // Guarda o maior custo de frete entre os produtos do pedido
        double highestShipping = 0;

        // Guarda o custo total do pedido
        double totalCost = 0;

        // -------- 1️⃣ Verificar disponibilidade --------
        for (Node order : orderProducts) {

            // Procura o produto no estoque usando o ID
            Node stock = findProduct(order.product.getId());

            // Se o produto não existir OU a quantidade em estoque for menor que a pedida
            if (stock == null || stock.quantity < order.quantity) {

                // Cancela o pedido
                return;
            }
        }

        // -------- 2️⃣ Calcular preço total --------
        for (Node order : orderProducts) {

            // Soma ao total o preço do produto multiplicado pela quantidade
            totalCost += order.product.getPrice() * order.quantity;

            // Atualiza o maior valor de frete entre os produtos
            highestShipping = Math.max(
                    highestShipping,
                    order.product.getShippingCost());
        }

        // Adiciona o maior frete ao valor total
        totalCost += highestShipping;

        // -------- 3️⃣ Verificar saldo do usuário --------
        if (user.getBalance() < totalCost) {

            // Se o saldo for insuficiente, cancela o pedido
            return;
        }

        // -------- 4️⃣ Atualizar saldo do usuário --------
        // Subtrai do saldo o valor total da compra
        ((User) user).setBalance(user.getBalance() - totalCost);

        // -------- 5️⃣ Atualizar estoque --------
        for (Node order : orderProducts) {

            // Busca o produto no estoque
            Node stock = findProduct(order.product.getId());

            // Diminui a quantidade do estoque
            stock.quantity -= order.quantity;
        }

        // -------- 6️⃣ Registrar o pedido do usuário --------
        // Adiciona todos os produtos comprados na lista de pedidos do usuário
        user.getOrders().addAll(orderProducts);
    }

    // Método auxiliar para encontrar um produto no estoque pelo ID
    private Node findProduct(int productId) {

        // Percorre todos os produtos em estoque
        for (Node node : products) {

            // Se encontrar o produto com o ID correspondente
            if (node.product.getId() == productId) {

                // Retorna o Node contendo o produto e a quantidade
                return node;
            }
        }

        // Caso não encontre o produto, retorna null
        return null;
    }
}
