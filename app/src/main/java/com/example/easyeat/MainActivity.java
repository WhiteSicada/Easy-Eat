package com.example.easyeat;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easyeat.Api.Holders.CategoryHolder;
import com.example.easyeat.Api.Holders.ItemHolder;
import com.example.easyeat.Api.Holders.SubCategoryHolder;
import com.example.easyeat.Api.Tasks.RestTaskCategories;
import com.example.easyeat.Api.Tasks.RestTaskItems;
import com.example.easyeat.Api.Tasks.RestTaskSubCategories;
import com.example.easyeat.adapter.CategoryPagerAdapter;
import com.example.easyeat.adapter.ItemAdapter;
import com.example.easyeat.adapter.OrderAdapter;
import com.example.easyeat.login.Login;
import com.example.easyeat.menuactivity.AboutusActivity;
import com.example.easyeat.menuactivity.ProfileActivity;
import com.example.easyeat.model.Category;
import com.example.easyeat.model.Item;
import com.example.easyeat.model.Order;
import com.example.easyeat.model.Solution;
import com.example.easyeat.model.SubCategory;
import com.example.easyeat.model.User;
import com.example.easyeat.util.CircleAnimationUtil;
import com.steelkiwi.library.IncrementProductView;
import com.steelkiwi.library.listener.OnStateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements ItemAdapter.IItemAdapterCallback, OrderAdapter.IOrderAdapterCallback
{

    private DrawerLayout drawer;
    private TextView txtCount;
    private TextView txtTotal;
    private RecyclerView rvOrder;
    private OrderAdapter orderAdapter;
    private User user;
    private ArrayList<Category> categoryList;
    private ArrayList<SubCategory> subCategoryList;
    private ArrayList<Item> itemList;
    private ArrayList<Solution> solutionList;
    private ArrayList<Order> orderList;



    @Override
    public void onIncreaseDecreaseCallback()
    {
        updateOrderTotal();
        updateBadge();
    }

    /**
     * The callback to see the detail of the item.
     */
    @Override
    public void onItemCallback(Item item)
    {
        dialogItemDetail(item);
    }

    /**
     * The callback to add item to cart with animation.
     */
    @Override
    public void onAddItemCallback(ImageView imageView, Item item)
    {
        addItemToCartAnimation(imageView, item, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        prepareData();

        // Find views
        drawer = findViewById(R.id.dlMain);
        txtTotal = findViewById(R.id.txtTotal);
        rvOrder = findViewById(R.id.rvOrder);
        TextView txtClearAll = findViewById(R.id.txtClearAll);
        Button btnCompleteOrder = findViewById(R.id.btnCompleteOrder);

        // set
        rvOrder.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        orderAdapter = new OrderAdapter(MainActivity.this, orderList);
        rvOrder.setAdapter(orderAdapter);

        // Get the ViewPager and set it's CategoryPagerAdapter so that it can display items
        ViewPager vpItem = (ViewPager) findViewById(R.id.vpItem);
        CategoryPagerAdapter categoryPagerAdapter = new CategoryPagerAdapter(getSupportFragmentManager(), MainActivity.this, solutionList);
        vpItem.setOffscreenPageLimit(categoryPagerAdapter.getCount());
        vpItem.setAdapter(categoryPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabCategory = findViewById(R.id.tabCategory);
        tabCategory.setupWithViewPager(vpItem);

        for (int i = 0; i < tabCategory.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabCategory.getTabAt(i);
            tab.setCustomView(categoryPagerAdapter.getTabView(i));
        }

        btnCompleteOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!orderList.isEmpty())
                {
                    dialogCompleteOrder();
                }

            }
        });

        txtClearAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!orderList.isEmpty())
                {
                    dialogClearAll();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cart, menu);

        final View actionCart = menu.findItem(R.id.actionCart).getActionView();

        txtCount = (TextView) actionCart.findViewById(R.id.txtCount);
        RelativeLayout rlCart = (RelativeLayout) actionCart.findViewById(R.id.rlCart);

        rlCart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                handleOrderDrawer();
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id ==R.id.profile)
        {
            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
            return true;
        }
        else if(id ==R.id.aboutus){
                Intent intent=new Intent(MainActivity.this, AboutusActivity.class);
                startActivity(intent);

            return true;
        }
        else if(id == R.id.logout){
                    Intent intent=new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                   return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    * Prepares sample data to be used within the application
    */
    private void prepareData()
    {
        categoryList = new ArrayList<Category>();
        subCategoryList = new ArrayList<SubCategory>();
        itemList = new ArrayList<Item>();
        solutionList = new ArrayList<Solution>();
        orderList = new ArrayList<Order>();

        categoryList.add(new Category(1, "All", R.drawable.chef));

        categoryList.add(new Category(2, "Molly's Dinner", R.drawable.mollysdiner));
        subCategoryList.add(new SubCategory(1, 2, "Hamburger"));
        subCategoryList.add(new SubCategory(2, 2, "Pizza"));
        subCategoryList.add(new SubCategory(3, 2, "Kebab"));
        subCategoryList.add(new SubCategory(4, 2, "Dessert"));

        categoryList.add(new Category(3, "STARBUCKS", R.drawable.starbucks));

        subCategoryList.add(new SubCategory(6, 3, "Coffee"));
        subCategoryList.add(new SubCategory(7, 3, "Tea"));

        categoryList.add(new Category(4, "MCDONALD'S ", R.drawable.mcddo));
        subCategoryList.add(new SubCategory(10, 4, "Burger"));
        subCategoryList.add(new SubCategory(11, 4, "Dessert"));
        subCategoryList.add(new SubCategory(12, 4, "Nugget"));
       /* subCategoryList.add(new SubCategory(13, 4, ""));
        subCategoryList.add(new SubCategory(14, 4, ""));
        subCategoryList.add(new SubCategory(15, 4, ""));
        subCategoryList.add(new SubCategory(16, 4, ""));
        subCategoryList.add(new SubCategory(17, 4, ""));*/

        categoryList.add(new Category(5, "PIZZA HUT", R.drawable.pizzahutlogo));
        subCategoryList.add(new SubCategory(18, 5, "Pizza"));
        subCategoryList.add(new SubCategory(19, 5, "Pasta"));
        subCategoryList.add(new SubCategory(20, 5, "Dessert"));
        /*subCategoryList.add(new SubCategory(21, 5, ""));
        subCategoryList.add(new SubCategory(22, 5, ""));
        subCategoryList.add(new SubCategory(23, 5, ""));
        subCategoryList.add(new SubCategory(24, 5, ""));*/

///////////////////////////////////////////MOLLY S DINNER////////////////////////////////////////////////////////////
        //Hamburger
        itemList.add(new Item(1, 2, 1, "Ultimate Hamburger", 80 , R.drawable.humbur));
        itemList.add(new Item(2, 2, 1, "Double Cheese Burger", 75, R.drawable.cheeseburger));

        //Pizza
        itemList.add(new Item(3, 2, 2, "Pepperoni", 45, R.drawable.pizzapepperoni));
        itemList.add(new Item(4, 2, 2, "Mixed Pizza", 40, R.drawable.mixedpizza));
        itemList.add(new Item(5, 2, 2, "Neapolitan Pizza", 50, R.drawable.pizza));

        //Kebab
        itemList.add(new Item(6, 2, 3, "Garlic-Tomato Kebab", 60, R.drawable.kebab));
       /* itemList.add(new Item(7, 2, 3, "Adana Kebab", 15.0, "http://www.muslumkebap.com/Contents/Upload/MuslumAdana_qyz0b34a.p3w.jpg"));
        itemList.add(new Item(8, 2, 3, "Eggplant Kebab", 18.0, "http://www.muslumkebap.com/Contents/Upload/MuslumPatlicanKebap_y41olk55.kpc.jpg"));*/
        itemList.add(new Item(9, 2, 3, "Shish Kebab", 60, R.drawable.shishkebab));

        //Dessert
        itemList.add(new Item(10, 2, 4, "Gâteau au chocolat", 30.3, R.drawable.gateauchocolat));
        itemList.add(new Item(11, 2, 4, "Gâteau au cerises", 40, R.drawable.gateaucerise));
        itemList.add(new Item(12, 2, 4, "Gâteau au caramel", 35, R.drawable.gateaucaramel));



        /////////////////////////////////////STARBUCKS/////////////////////////////////////////////
        //CoffeeStarbucks
        itemList.add(new Item(15, 3, 6, "Coffee Americano", 20, R.drawable.coffeeamer));
        itemList.add(new Item(16, 3, 6, "Cappuccino", 22, R.drawable.cappuccino));
        itemList.add(new Item(17, 3, 6, "Coffee Misto", 20, R.drawable.miiiiiiiiiisto));
        itemList.add(new Item(18, 3, 6, "Coffee Latte", 20, R.drawable.latte));


        //TeaStarbucks
        itemList.add(new Item(19, 3, 7, "Chai Tea", 20, R.drawable.hottea));
        itemList.add(new Item(20, 3, 7, "Honey Citrus Mint Tea", 20, R.drawable.citrusminttea));
        itemList.add(new Item(21, 3, 7, "Iced Black Tea", 21, R.drawable.icedtea));




        ////////////////////////////////////////////////////////////MCDOOOOOOO////////////////////////////////////////////////////////////////////






        //burger
        itemList.add(new Item(22, 4, 10, "Big Mac", 60, R.drawable.bigmac));
        itemList.add(new Item(23, 4, 10, "Big Tasty", 63, R.drawable.bigtasty));
        itemList.add(new Item(24, 4, 10, "Wrap Chicken", 53, R.drawable.wrapchicken));
        itemList.add(new Item(25, 4, 10, "McChicken", 50, R.drawable.mcchicken));
        itemList.add(new Item(26, 4, 10, "Cheeseburger", 72, R.drawable.cheeseburger));
        itemList.add(new Item(27, 4, 10, "Triple Cheese Burger", 80, R.drawable.triple));
        itemList.add(new Item(28, 4, 10, "Hamburger Deluxe", 89, R.drawable.hamburgerluxe));
        itemList.add(new Item(29, 4, 10, "Double Big Mac", 70, R.drawable.bigmacdouble));



        //Dessert
        itemList.add(new Item(30, 4, 11, "Chocolate Shake", 20,R.drawable.chocoshake));
        itemList.add(new Item(31, 4, 11, "Vanilla Shake", 20, R.drawable.vanillashake));
        itemList.add(new Item(32, 4, 11, "Stawberry Shake", 20, R.drawable.stawberryshake));
        itemList.add(new Item(33, 4, 11, "McFlurry OREO", 19, R.drawable.mcflurryoreo));

        //nugget

        itemList.add(new Item(34, 4, 12, "McNugget", 22, R.drawable.nugget));




        ////////////////////////////////////////////////PIZZA HUT//////////////////////////////////////////////////////////////////////////////

        // pizza
        itemList.add(new Item(35, 5, 18, "Carnivore", 80, R.drawable.carnivore));
        itemList.add(new Item(36, 5, 18, "Margheritta", 89, R.drawable.margheerita1));

        //pasta
        itemList.add(new Item(37, 5, 19, "Lasagna Classico ", 59, R.drawable.lasagnaclassico));
        itemList.add(new Item(38, 5, 19, "Jamaica", 57, R.drawable.jamaica));


        //Dessert
        itemList.add(new Item(39, 5, 20, "Kitkat Pops", 50, R.drawable.kitkatpops));
        itemList.add(new Item(40, 5, 20, "Strawberry Waffle", 50, R.drawable.strawberry));
        itemList.add(new Item(41, 5, 20, "Chocolat Waffle", 50, R.drawable.chocolatewaffle));
       /* categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        itemList = new ArrayList<>();
        solutionList = new ArrayList<>();
        orderList = new ArrayList<>();

        int[] categoryListImages = {R.drawable.chef,R.drawable.mollysdiner,R.drawable.starbucks,R.drawable.mcddo,R.drawable.pizzahutlogo};
        int[] itemListImages = {R.drawable.humbur,R.drawable.cheeseburger,R.drawable.pizzapepperoni,R.drawable.mixedpizza,R.drawable.pizza,
                R.drawable.kebab,R.drawable.shishkebab,R.drawable.gateauchocolat,R.drawable.gateaucerise,R.drawable.gateaucaramel,
                R.drawable.coffeeamer,R.drawable.cappuccino,R.drawable.miiiiiiiiiisto,R.drawable.latte,R.drawable.hottea,
                R.drawable.citrusminttea,R.drawable.icedtea,R.drawable.bigmac,R.drawable.bigtasty,R.drawable.wrapchicken,
                R.drawable.mcchicken,R.drawable.cheeseburger,R.drawable.triple,R.drawable.hamburgerluxe,R.drawable.bigmacdouble,
                R.drawable.chocoshake,R.drawable.vanillashake,R.drawable.stawberryshake,R.drawable.mcflurryoreo,R.drawable.nugget,
                R.drawable.carnivore,R.drawable.margheerita1,R.drawable.lasagnaclassico,R.drawable.jamaica,R.drawable.kitkatpops,R.drawable.strawberry,R.drawable.chocolatewaffle};


        try {
            CategoryHolder[] categoryHolders = new RestTaskCategories().execute().get();
            SubCategoryHolder[] subCategoryHolders = new RestTaskSubCategories().execute().get();
            ItemHolder[] itemHolders = new RestTaskItems().execute().get();
            fillCategoryList(categoryHolders,categoryListImages);
            fillSubCategoryList(subCategoryHolders);
            fillItemList(itemHolders,itemListImages);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/






        /*
         * Populates solutionList.
         * For each category, gets sub-categories, items and a map
         * showing the connection between the sub-category and its items.
         */
        for (Category categoryItem : categoryList)
        {
            // Temporary list of the current sıb-categories
            List<SubCategory> tempSubCategoryList = new ArrayList<>();

            // Temporary list of the current items
            List<Item> tempItemList = new ArrayList<>();

            // Temporary map
            Map<SubCategory, ArrayList<Item>> itemMap = new HashMap<>();

            // categoryId == 1 means category with all items and sub-categories.
            // That's why i add all the sub-categories and items directly.
            if (categoryItem.getId() == 1)
            {
                itemMap = getItemMap(subCategoryList);

                solutionList.add(new Solution(categoryItem, subCategoryList, itemList, itemMap));
            }
            else
            {
                tempSubCategoryList = getSubCategoryListByCategoryId(categoryItem.getId());
                tempItemList = getItemListByCategoryId(categoryItem.getId());
                itemMap = getItemMap((ArrayList<SubCategory>) tempSubCategoryList);

                solutionList.add(new Solution(categoryItem, tempSubCategoryList, tempItemList, itemMap));
            }
        }
    }

    private void fillItemList(ItemHolder[] itemHolders, int[] itemListImages) {
        for (int i=0;i< itemListImages.length;i++){
            itemList.add(new Item(itemHolders[i], itemListImages[i]));
        }
    }

    private void fillSubCategoryList(SubCategoryHolder[] subCategoryHolders) {
        for (SubCategoryHolder subCategoryHolder : subCategoryHolders){
            subCategoryList.add(new SubCategory(subCategoryHolder));
        }
    }

    private void fillCategoryList(CategoryHolder[] categoryHolders, int[] categoryListImages) {
        for (int i=0;i< categoryListImages.length;i++){
            categoryList.add(new Category(categoryHolders[i], categoryListImages[i]));
        }
    }

    /**
     * Gets the sub-categories belonging to a category
     *
     * @param categoryId The id of the current category.
     *
     * Returns the sub-categories belonging to that category as a list using the id of the current category.
     * @return a list of sub-category
     */
    private ArrayList<SubCategory> getSubCategoryListByCategoryId(int categoryId)
    {
        ArrayList<SubCategory> tempSubCategoryList = new ArrayList<>();

        for (SubCategory subCategory : subCategoryList)
        {
            if (subCategory.getCategoryId() == categoryId)
            {
                tempSubCategoryList.add(new SubCategory(subCategory));
            }
        }

        return tempSubCategoryList;
    }

    /**
     * Gets the items belonging to a category
     *
     * @param categoryId The id of the current category.
     *
     * Returns the items belonging to that category as a list using the id of the current category.
     * @return a list of items
     */
    private ArrayList<Item> getItemListByCategoryId(int categoryId)
    {
        ArrayList<Item> tempItemList = new ArrayList<>();

        for (Item item : itemList)
        {
            if (item.getCategoryId() == categoryId)
            {
                tempItemList.add(item);
            }
        }

        return tempItemList;
    }

    /**
     * Gets the items belonging to a sub-category
     *
     * @param subCategoryId The id of the current sub-category.
     *
     * Returns the item belonging to that sub-category as a list
     * using the id of the current sub-category.
     * @return a list of items
     */
    private ArrayList<Item> getItemListBySubCategoryId(int subCategoryId)
    {
        ArrayList<Item> tempItemList = new ArrayList<>();

        for (Item item : itemList)
        {
            if (item.getSubCategoryId() == subCategoryId)
            {
                tempItemList.add(item);
            }
        }

        return tempItemList;
    }

    /**
     * Gets a Map which has the key is the sub-category
     * and the value is the items of that sub-category.
     * That Map will also be used in the Sectioned RecyclerView.
     *
     * @param subCategoryList sub-categories which is owned by a category
     *
     * @return a Map
     */
    private Map<SubCategory, ArrayList<Item>> getItemMap(ArrayList<SubCategory> subCategoryList)
    {
        Map<SubCategory, ArrayList<Item>> itemMap = new HashMap<>();

        for (SubCategory subCategory : subCategoryList)
        {
            itemMap.put(subCategory, getItemListBySubCategoryId(subCategory.getId()));
        }

        return itemMap;
    }

    /*
    * Shows the detail of item
    */
    private void dialogItemDetail(final Item item)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_item_detail, null);

        final IncrementProductView incrementProductView = (IncrementProductView) view.findViewById(R.id.productView);
        TextView txtItemName = (TextView) view.findViewById(R.id.txtItemName);
        final TextView txtUnitPrice = (TextView) view.findViewById(R.id.txtUnitPrice);
        final TextView txtExtendedPrice = (TextView) view.findViewById(R.id.txtExtendedPrice);
        final TextView txtQuantity = (TextView) view.findViewById(R.id.txtQuantity);
        final ImageView imgThumbnail = (ImageView) view.findViewById(R.id.imgThumbnail);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);

        txtItemName.setText(item.getName());
        txtUnitPrice.setText(String.format("%.2f", item.getUnitPrice()));
        txtQuantity.setText("1");
        txtExtendedPrice.setText(String.format("%.2f", item.getUnitPrice() * 1));

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DetailDialogAnimation;
        dialog.show();

        Glide.with(MainActivity.this)
                .load(item.getUrl())
                .into(imgThumbnail);

        incrementProductView.getAddIcon();

        incrementProductView.setOnStateListener(new OnStateListener()
        {
            @Override
            public void onCountChange(int count)
            {
                txtQuantity.setText(String.valueOf(count));
                txtExtendedPrice.setText(String.format("%.2f", item.getUnitPrice() * count));
            }

            @Override
            public void onConfirm(int count) {

            }

            @Override
            public void onClose() {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addItemToCartAnimation(imgThumbnail, item, Integer.parseInt(txtQuantity.getText().toString()));

                dialog.dismiss();
            }
        });
    }

    /**
     * Animates when item is added to the cart.
     * The animation will start at item's ImageView,
     * continue up to cart MenuItem at ToolBar.
     * This will take 0.3 seconds. When the animation is over,
     * the item will be added to the cart in quantities.
     *
     * @see CircleAnimationUtil
     * @see com.example.easyeat.util.CircleImageView
     *
     * @param targetView
     * @param item element wanted to add to cart
     * @param quantity the amount of how many item you want to add to cart
     */
    private void addItemToCartAnimation(ImageView targetView, final Item item, final int quantity)
    {
        RelativeLayout destView = (RelativeLayout) findViewById(R.id.rlCart);

        new CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(300).
                setDestView(destView).setAnimationListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                addItemToCart(item, quantity);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).startAnimation();
    }

    /*
    * Adds the item to order list.
    */
    private void addItemToCart(Item item, int quantity)
    {
        boolean isAdded = false;

        for (Order order : orderList)
        {
            if (order.getItem().getId() == item.getId())
            {
                //if item already added to cart, dont add new order
                //just add the quantity
                isAdded = true;
                order.setQuantity(order.getQuantity() + quantity);
                order.setExtendedPrice(order.getExtendedPrice() + item.getUnitPrice());
                break;
            }
        }

        //if item's not added yet
        if (!isAdded)
        {
            orderList.add(new Order(item, quantity));
        }

        orderAdapter.notifyDataSetChanged();
        rvOrder.smoothScrollToPosition(orderList.size() - 1);
        updateOrderTotal();
        updateBadge();
    }

    /*
    * Updates the value of the badge
    */
    private void updateBadge()
    {
        if (orderList.isEmpty())
        {
            txtCount.setVisibility(View.INVISIBLE);
        } else
        {
            txtCount.setVisibility(View.VISIBLE);
            txtCount.setText(String.valueOf(orderList.size()));
        }
    }

    /*
    * Gets the total price of all products added to the cart
    */
    private double getOrderTotal()
    {
        double total = 0.0;

        for (Order order : orderList)
        {
            total += order.getExtendedPrice();
        }

        return total;
    }

    /*
    * Updates the total price of all products added to the cart
    */
    private void updateOrderTotal()
    {
        double total = getOrderTotal();
        txtTotal.setText(String.format("%.2f", total));
    }

    /*
    * Closes or opens the drawer
    */
    private void handleOrderDrawer()
    {
        if (drawer != null)
        {
            if (drawer.isDrawerOpen(GravityCompat.END))
            {
                drawer.closeDrawer(GravityCompat.END);
            } else
            {
                drawer.openDrawer(GravityCompat.END);
            }
        }
    }

    /*
    * Makes the cart empty
    */
    private void dialogClearAll()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else
        {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(R.string.clear_all)
                .setMessage(R.string.delete_all_orders)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        clearAll();
                        showMessage(true, getString(R.string.cart_clean));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                      public void onClick(DialogInterface dialog, int which)
                    {
                        // do nothing
                    }
                })
                .show();
    }

    /*
    * Completes the order
    */
    private void dialogCompleteOrder()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else
        {
            builder = new AlertDialog.Builder(MainActivity.this);
        }
        builder.setTitle(getString(R.string.complete_order))
                .setMessage(getString(R.string.complete_order_question))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("orderList",orderList);
                        intent.putExtras(bundle);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // do nothing
                    }
                })
                .show();
    }



    /*
    * Clears all orders from the cart
    */
    private void clearAll()
    {
        orderList.clear();
        orderAdapter.notifyDataSetChanged();

        updateBadge();
        updateOrderTotal();
        handleOrderDrawer();
    }

    /*
    * Shows a message by using Snackbar
    */
    private void showMessage(Boolean isSuccessful, String message)
    {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);

        if (Boolean.TRUE.equals(isSuccessful))
        {
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        } else
        {
            snackbar.getView().setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPomegranate));
        }

        snackbar.show();
    }
}
