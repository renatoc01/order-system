# order-system

Create a system for an ecommerce company to process customer orders based on product availability and account balance. the given class Node has two fields IProduct and int Implement: 

1 - Product class that implements IProduct Properties: Id Name Price ShippingCost Constructor that accepts and sets all properties 

2 - User class that implements IUser w ith Properties: Id Name Balance Orders(List) Constructor that accepts id, name, and balance Initialize Orders as a new List in the constructor 

3 - Company class that implements ICompany with Properties Products(List) Users(List) 

Method 
MakeOrder that: 
Takes a list of products and a user as input 
Checks product availability 
Determines highest shipping cost among the products ordered 
Calculates total cost (sum of price x quantity + highest shipping) 
Verifies user has sufficient funds 
Update user balance, product quantities, and users orders 
AddProduct that 
Takes a product and quantity as input 
If the product exists, it updates the quantity 
If the product does not exist, it adds it with the quantity 
AddUser: 
Takes a user as input
Adds the user to the list 

Example 
With 2 products(Laptop: 20 price, 5 shipping, 20 quantity; Phone: 30 price, 3 shipping, 10 quantity) and a user (User1 with 100 balance); 

When ordering 3 Laptops and 1 Phone: 
Availability check: 
Passed Highest shipping: 5 Total cost: (3 x 20) + (1 x 30) + 5 = 95
Balance check: Passed (100 > 95) 

After purchase: 
New product quantities: 17 Laptops, 9 Phones 
User balance updated to 5 
Products are added to User1's orders 

Constraints 
Product IDs are distinct for each product 

Input format for custom testing The first line contains an integer n, the n umber of products. Each of the next n lines contains the (Id, Name, Price, ShippingCost, Quantity) of the product separated by commas. The next line contains an integer m, the number of users. Each of the next m lines contains the (Id, Name, Balance) of the user. The next line contains an integer k, the number of orders. Each of the next k lines contains the (UserId,ProductId|Quantity,ProductId|Quantity...) of the order information. ("," to separate products, "|" to separate product and order quantity). 

Sample Case 0 
Sample Input 
2
1,product1,20,2,20 
2,product2,30,1,10 
1 
1,user1,500 
1 
1,1|5,2|2 

Sample output 
product1:15 
product2:8 

Explanation Looking at the order, user1 orders 5 units of product1 and 2 units of product2. The higher price for shipping is 2 for product1. There are plenty of units on hand, 20 of product1 and 10 of product2. The total cost is (5 * 20) + (2 * 30) + 2 = 162. The user has enough funds, so the order is filled and product quantities are updated. 

The main code is already implemented
