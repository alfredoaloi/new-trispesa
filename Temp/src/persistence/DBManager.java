package persistence;

import java.util.ArrayList;
import java.util.ListIterator;

import exceptions.DBOperationException;
import model.Category;
import model.Customer;
import model.DeliveryAddress;
import model.PaymentMethod;
import model.Product;
import model.SuperMarket;
import persistence.dao.AdministratorDao;
import persistence.dao.CategoryDao;
import persistence.dao.CustomerDao;
import persistence.dao.DeliveryAddressDao;
import persistence.dao.OrderDao;
import persistence.dao.PaymentMethodDao;
import persistence.dao.ProductDao;
import persistence.dao.SuperMarketDao;
import persistence.dao.jdbc.AdministratorDaoJdbc;
import persistence.dao.jdbc.CategoryDaoJdbc;
import persistence.dao.jdbc.ProductDaoJdbc;
import persistence.dao.jdbc.SuperMarketDaoJdbc;

public class DBManager {

	private static DBManager istance = null;
	private static DataSource dataSource = null;

	private ArrayList<Customer> customers;
	private ArrayList<Product> products;
	private ArrayList<SuperMarket> superMarkets;
	private ArrayList<Category> categories;
	private ArrayList<Category> macroCategories;

	public static DBManager getInstance() {
		if (istance == null)
			istance = new DBManager();
		return istance;
	}

	private DBManager() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource = new DataSource("jdbc:postgresql://rogue.db.elephantsql.com:5432/zqnyocaq", "zqnyocaq",
					"DJ8nD9eyeT4VjZAvTnAvUDcc-ExoZTN_");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		for (Category category : getCategoryDao().retrieveAll()) {
			if (category.getParent() != null) {
				System.out.println(category.getId() + " " + category.getName() + " " + category.getParent().getName());
			} else {
				System.out.println(category.getId() + " " + category.getName() + " null");
			}
		}

//		// cazzate sul db
//		superMarkets = new ArrayList<SuperMarket>();
//		superMarkets.add(new SuperMarket("conad", "cosenza", "via della banana, 33", true, 1, 1));
//		superMarkets.add(new SuperMarket("coop", "cosenza", "via della vita, 33", false, 2, 2));
//		superMarkets.add(new SuperMarket("lidl", "cosenza", "via della morte, 33", true, 3, 3));
//		superMarkets.add(new SuperMarket("auchan", "cosenza", "via della citre, 33", true, 4, 4));
//		superMarkets.add(new SuperMarket("conad", "rende", "via ciccio bello, 33", false, 5, 5));
//		SuperMarket conad = superMarkets.get(0);
//
//		categories = new ArrayList<Category>();
//		macroCategories = new ArrayList<Category>();
//		Category pasta = new Category("pasta", null);
//		Category pastaIntegrale = new Category("integrale", pasta);
//		Category pastaBianca = new Category("bianca", pasta);
//		Category pane = new Category("pane", null);
//		Category paneBianco = new Category("bianco", pane);
//		Category paneIntegrale = new Category("integrale", pane);
//		categories.add(pastaIntegrale);
//		categories.add(pastaBianca);
//		categories.add(paneBianco);
//		categories.add(paneIntegrale);
//		macroCategories.add(pasta);
//		macroCategories.add(pane);
//
//		products = new ArrayList<Product>();
//		products.add(new Product(1, "pasta", 1.1, 123.0, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(2, "pasta", 1.1, 123.0, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(3, "pasta", 1.1, 123.0, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(4, "a", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(5, "b", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(6, "c", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(7, "d", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(8, "e", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(9, "f", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(10, "pasta", 1000, 123, conad, true, paneBianco, 1, ""));
//		products.add(new Product(11, "pasta", 1.1, 123, conad, true, pastaIntegrale, 1, ""));
//		products.add(new Product(12, "pasta", 1.1, 123, superMarkets.get(1), true, pastaIntegrale, 1, ""));
//
//		Customer customer = new Customer("Goffredson", "bellecose", "Alfredo", "Aloi", "a.a@a.a",
//				LocalDate.of(1999, 11, 3), LocalDate.of(2019, 3, 3), null, null);
//		customer.addDeliveryAddress(new DeliveryAddress("Iatlia", "Cosenza", "via della banana"));
//		customer.addDeliveryAddress(new DeliveryAddress("Iatlia", "43253", "via della banana"));
//		customer.addDeliveryAddress(new DeliveryAddress("Iatlia", "Cos5436enza", "via della banana"));
//		customer.addDeliveryAddress(new DeliveryAddress("Iat42lia", "Cosenza", "via del4352435la banana"));
//
//		customer.addPaymentMethod(new PaymentMethod(1232345, "Boh", 11, 2021, 950));
//		customer.addPaymentMethod(new PaymentMethod(12345, "Bociaoh", 14321, 2021, 950));
//		customer.addPaymentMethod(new PaymentMethod(123345, "Boh", 11, 20221, 950));
//		customers = new ArrayList<Customer>();
//		customers.add(customer);
	}

	public AdministratorDao getAdministratorDao() {
		return new AdministratorDaoJdbc(dataSource);
	}


	public CategoryDao getCategoryDao() {
		return new CategoryDaoJdbc(dataSource);
	}

	public CustomerDao getCustomerDao() {
		return null;
	}

	public DeliveryAddressDao getDeliveryAddressDao() {
		return null;
	}

	public OrderDao getOrderDao() {
		return null;
	}

	public PaymentMethodDao getPaymentMethodDao() {
		return null;
	}

	public ProductDao getProductDao() {
		return new ProductDaoJdbc(dataSource);
	}

	public SuperMarketDao getSuperMarketDao() {
		return new SuperMarketDaoJdbc(dataSource);
	}

	public ArrayList<Category> getMacroCategories() {
		return macroCategories;
	}

	public void addSupermarket(SuperMarket superMarket) throws DBOperationException {
		for (SuperMarket temp : superMarkets) {
			if (temp.equals(superMarket)) {
				throw new DBOperationException("Il supermercato � gi� presente nel database",
						superMarket.toString());
			}
		}
		superMarkets.add(superMarket);
	}

	public void addProduct(Product product) throws DBOperationException {
		for (Product temp : products) {
			if (temp.getBarcode() == product.getBarcode() && temp.getSuperMarket().equals(product.getSuperMarket())) {
				throw new DBOperationException("Il prodotto � gi� presente nel database", product.toString());
			}
		}
		products.add(product);
	}

	public SuperMarket getSuperMarketByID(String superMarketString) {
		for (SuperMarket superMarket : superMarkets) {
			String temp = "(" + superMarket.getName() + "," + superMarket.getCity() + "," + superMarket.getAddress()
					+ ")";
			if (superMarketString.equals(temp)) {
				return superMarket;
			}
		}
		return null;
	}

	public Category getCategoryByFamilyName(String familyName) {
		for (Category category : categories) {
			if (category.getFamilyName().equals(familyName)) {
				return category;
			}
		}
		return null;
	}

	public void removeProductByID(int barcode, SuperMarket superMarket) throws DBOperationException {
		for (Product temp : products) {
			if (temp.getBarcode() == barcode && temp.getSuperMarket().equals(superMarket)) {
				products.remove(temp);
				return;
			}
		}
		throw new DBOperationException("Il prodotto da eliminare non � stato trovato", "null");
	}

	public Product getProductByID(long barcode, SuperMarket superMarket) {
		for (Product temp : products) {
			if (temp.getBarcode() == barcode && temp.getSuperMarket().equals(superMarket)) {
				return temp;
			}
		}
		return null;
	}

	public void modifyProduct(Product product) throws DBOperationException {
		Product temp = getProductByID(product.getBarcode(), product.getSuperMarket());
		if (temp == null) {
			throw new DBOperationException("Il prodotto da modificare non � stato trovato", "null");
		}
		temp.setPrice(product.getPrice());
		temp.setQuantity(product.getQuantity());
		temp.setOffBrand(product.isOffBrand());
	}

	public void removeAffiliateSuperMarketByID(String superMarketString) throws DBOperationException {
		SuperMarket temp = getSuperMarketByID(superMarketString);
		if (temp == null) {
			throw new DBOperationException("Il supermercato da rimuovere non � stato trovato", "null");
		}
		temp.setAffiliate(false);

		ArrayList<Product> tempProducts = new ArrayList<Product>();
		for (Product product : products) {
			if (!(product.getSuperMarket().equals(temp))) {
				tempProducts.add(product);
			}
		}
		products = tempProducts;
	}

	public void modifySuperMarket(String oldSuperMarketString, SuperMarket superMarket) throws DBOperationException {
		SuperMarket temp = getSuperMarketByID(oldSuperMarketString);
		if (temp == null) {
			throw new DBOperationException("Il supermercato da modificare non � stato trovato", "null");
		}
		temp.setName(superMarket.getName());
		temp.setCity(superMarket.getCity());
		temp.setAddress(superMarket.getAddress());
		temp.setLatitude(superMarket.getLatitude());
		temp.setLongitude(superMarket.getLongitude());
	}

	public void addAffiliateSuperMarketByID(String superMarketString) throws DBOperationException {
		SuperMarket temp = getSuperMarketByID(superMarketString);
		if (temp == null) {
			throw new DBOperationException("Il supermercato da affiliare non � stato trovato", "null");
		}
		temp.setAffiliate(true);
	}

	public ArrayList<SuperMarket> getAffiliateSuperMarkets() {
		ArrayList<SuperMarket> temp = new ArrayList<SuperMarket>();
		for (SuperMarket superMarket : superMarkets) {
			if (superMarket.isAffiliate()) {
				temp.add(superMarket);
			}
		}
		return temp;
	}

	public ArrayList<Product> getProductsByCategory(String category) {
		ArrayList<Product> productsByCategory = new ArrayList<Product>();
		for (Product i : products) {
			if (i.getCategory().getName().equals(category)) {
				productsByCategory.add(i);
			}
		}
		return productsByCategory;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public ArrayList<Category> getCategories() {
		return categories;
	}

	public ArrayList<Category> getLeafCategories() {
		ArrayList<Category> leafCategories = new ArrayList<Category>();
		for (Category c : this.categories)
			if (!(leafCategories.contains(c)))
				leafCategories.add(c);
		return leafCategories;
	}

	public ArrayList<SuperMarket> getSuperMarkets() {
		return superMarkets;
	}

	public ArrayList<Product> getProductsByName(String nomeProdotto) {
		ArrayList<Product> prodotti = new ArrayList<Product>();
		for (Product p : this.products)
			if (p.getName().matches(".*" + nomeProdotto + ".*"))
				prodotti.add(p);
		return prodotti;
	}

	public Customer getCustomerByID(String username) {
		for (Customer customer : customers) {
			if (customer.getUsername().equals(username)) {
				return customer;
			}
		}
		return null;
	}

	public void removePaymentMethod(Customer customer, PaymentMethod paymentMethod) throws DBOperationException {
		for (Customer temp : customers) {
			if (temp.equals(customer)) {
				temp.removePaymentMethod(paymentMethod);
				return;
			}
		}
		throw new DBOperationException("Non � stato possibile eliminare il metodo di pagamento",
				paymentMethod.toString());
	}

	public void removeDeliveryAddress(Customer customer, DeliveryAddress deliveryAddress) throws DBOperationException {
		for (Customer temp : customers) {
			if (temp.equals(customer)) {
				temp.removeDeliveryAddress(deliveryAddress);
				return;
			}
		}
		throw new DBOperationException("Non � stato possibile eliminare l'indirizzo di consegna",
				deliveryAddress.toString());
	}

	public void escludiProdotti(String categoria, ArrayList<Product> prodotti) {
		ListIterator<Product> iter = prodotti.listIterator();
		while (iter.hasNext()) {
			// System.out.println("Il prodotto " + iter.next().getName() + "è di categoria
			// " + iter.next().getCategory().getName());
			if (!(iter.next().getCategory().getName().equals(categoria))) {
				iter.remove();
			}
		}

	}

}