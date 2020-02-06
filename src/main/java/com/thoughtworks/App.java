package com.thoughtworks;

import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    ArrayList selectedItemsArr = getSelectedItems(selectedItems);
    String[] selectedItemsId=(String[])selectedItemsArr.get(0);
    int[] selectedItemsNum=(int[])selectedItemsArr.get(1);

    ArrayList SelectedItemsTotal = printSelectedItems(selectedItemsId,selectedItemsNum);
    int totalPrice = (int) SelectedItemsTotal.get(0);
    String summary = (String)SelectedItemsTotal.get(1);

    ArrayList printDiscountInfo=choseDiscount( totalPrice,selectedItemsId,selectedItemsNum);
    int hasDiscount= (int)printDiscountInfo.get(0);
    String discountInfo=(String)printDiscountInfo.get(1);
    int totalPriceSummary=(int)printDiscountInfo.get(2);

    summary = printSummary(hasDiscount,discountInfo,totalPriceSummary,summary);
    return summary;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }

  /**
   * 获取购买菜品的信息
   */
  public static ArrayList getSelectedItems(String selectedItems) {
    String[] selectedItemsSplit = selectedItems.split(",");
    String[] selectedItemsId =new String[selectedItemsSplit.length];
    int[] selectedItemsNum =new int[selectedItemsSplit.length];
    for(int i =0;i<selectedItemsSplit.length;i++){
      selectedItemsId[i] = selectedItemsSplit[i].split(" x ")[0] ;
      selectedItemsNum[i] = Integer.parseInt(selectedItemsSplit[i].split(" x ")[1]);
    }
    ArrayList selectedItemsArr = new ArrayList();
    selectedItemsArr.add(selectedItemsId);
    selectedItemsArr.add(selectedItemsNum);
    return selectedItemsArr;
  }
  /**
   *  打印购买菜品的信息
   */
  public static ArrayList printSelectedItems(String[] selectedItemsId, int[] selectedItemsNum) {
    String[] itemIds = getItemIds();
    String[] itemNames = getItemNames();
    double[] itemPrices = getItemPrices();

    String summary = "============= 订餐明细 =============\n";
    int totalPrice = 0;
    for(int i=0;i<selectedItemsId.length;i++){
      int itemTotalPrice = 0;
      int indexOfItem = Arrays.binarySearch(itemIds,selectedItemsId[i]);
      itemTotalPrice += Integer.valueOf(selectedItemsNum[i])*itemPrices[indexOfItem];
      totalPrice += itemTotalPrice;
      summary += (itemNames[indexOfItem]+" x "+selectedItemsNum[i]+" = "+itemTotalPrice+"元\n");
    }

    ArrayList SelectedItemsTotal = new ArrayList();
    SelectedItemsTotal.add(totalPrice);
    SelectedItemsTotal.add(summary);
    return SelectedItemsTotal;
  }

  /**
   * 第一种优惠
   */
  public static int hasDiscountOne(int totalPrice,int totalPriceSummary) {
    if(totalPrice >= 30){
      totalPriceSummary -= 6;
    }
    return totalPriceSummary;
  }

  public static ArrayList hasDiscountOneSummary(int totalPrice,String discountInfo,int hasDiscount) {
    if(totalPrice >= 30){
      discountInfo = "满30减6元，省6元\n";
      hasDiscount = 1;
    }
    ArrayList discountOneSummary = new ArrayList();
    discountOneSummary.add(hasDiscount);
    discountOneSummary.add(discountInfo);
    return discountOneSummary;
  }
  /**
   * 第二种优惠
   */
  public static int hasDiscountTwo(int totalPriceSummary,String[] selectedItemsId,int[]selectedItemsNum) {
    String[] itemIds = getItemIds();
    double[] itemPrices = getItemPrices();
    String[] halfPriceIds = getHalfPriceIds();

    for (int i=0;i<selectedItemsId.length;i++){
      if (Arrays.asList(halfPriceIds).contains(selectedItemsId[i])){
        int indexOfItem = Arrays.binarySearch(itemIds,selectedItemsId[i]);
        totalPriceSummary -= 0.5*itemPrices[indexOfItem]*(Integer.valueOf(selectedItemsNum[i]));
      }
    }
    return totalPriceSummary;
  }

  public static ArrayList hasDiscountTwoSummary(int totalPrice, int totalPriceSummary, String[] selectedItemsId, String discountInfo, int hasDiscount) {
    String[] itemIds = getItemIds();
    String[] itemNames = getItemNames();
    String[] halfPriceIds = getHalfPriceIds();
    String halfItemName = "";

    for (int i=0;i<selectedItemsId.length;i++){
      if (Arrays.asList(halfPriceIds).contains(selectedItemsId[i])){
        int indexOfItem = Arrays.binarySearch(itemIds,selectedItemsId[i]);
        halfItemName += itemNames[indexOfItem]+"，";
      }
    }
    discountInfo = "指定菜品半价("+halfItemName.substring(0,halfItemName.length() - 1)+")，省"+(totalPrice-totalPriceSummary)+"元\n";
    hasDiscount = 1;
    ArrayList discountTwoSummary = new ArrayList();
    discountTwoSummary.add(hasDiscount);
    discountTwoSummary.add(discountInfo);
    return discountTwoSummary;
  }
  /**
   * 选择使用哪种优惠
   */
  public static ArrayList choseDiscount(int totalPrice,String[] selectedItemsId,int[] selectedItemsNum) {
    int hasDiscount = 0;
    int totalPriceSummary = totalPrice;
    String discountInfo = "";
    int discountOneTotal =  hasDiscountOne(totalPrice,totalPriceSummary);
    int discountTwoTotal =  hasDiscountTwo(totalPriceSummary, selectedItemsId,selectedItemsNum);
    if(discountOneTotal<=discountTwoTotal){
      ArrayList discountOneSummary = hasDiscountOneSummary(totalPrice,discountInfo,hasDiscount);
      discountOneSummary.add(discountOneTotal);
      return discountOneSummary;
    }else{
      ArrayList discountTwoSummary = hasDiscountTwoSummary(totalPrice,discountTwoTotal, selectedItemsId,discountInfo,hasDiscount);
      discountTwoSummary.add(discountTwoTotal);
      return  discountTwoSummary;
    }
  }




}
