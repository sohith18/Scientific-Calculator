package org.calculatorapp;

import java.util.Scanner;


public class Calculator{
    public double sqRoot(double a){
        return Math.sqrt(a);
    }

    public int factorial(int a){
        if(a == 0)
            return 1;
        int fact = 1;
        for(int i = 1; i <= a; i++){
            fact *= i;
        }
        return fact;
    }

    public double naturalLog(double a){
        return Math.log(a);
    }

    public double power(double a, double b){
        return Math.pow(a, b);
    }
    public static void main(String[] args){
        //Making command line driven calculator
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Available operations:");
            System.out.println("1. Square Root");
            System.out.println("2. Factorial");
            System.out.println("3. Natural Log");
            System.out.println("4. Power");
            System.out.println("5. Exit");
            System.out.print("Choose an operation (1-5): ");

            int option = sc.nextInt();

            if(option == 5){
                System.out.println("Exited the calculator.");
                break;
            }
            Calculator calc = new Calculator();

            switch(option){
                case 1:
                    System.out.print("Enter a number: ");
                    double num1 = sc.nextDouble();
                    System.out.println("Square Root: " + calc.sqRoot(num1));
                    break;
                case 2:
                    System.out.print("Enter a number: ");
                    int num2 = sc.nextInt();
                    System.out.println("Factorial: " + calc.factorial(num2));
                    break;
                case 3:
                    System.out.print("Enter a number: ");
                    double num3 = sc.nextDouble();
                    System.out.println("Natural Log: " + calc.naturalLog(num3));
                    break;
                case 4:
                    System.out.print("Enter base number: ");
                    double base = sc.nextDouble();
                    System.out.print("Enter exponent number: ");
                    double exponent = sc.nextDouble();
                    System.out.println("Power: " + calc.power(base, exponent));
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

            System.out.println("-----------------------------");

        }

        sc.close();

    }
}