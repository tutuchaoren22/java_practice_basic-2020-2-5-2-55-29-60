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
}
