import java.util.*;
public class modiAlgorithm{
    // global variable
    static int row=0,col=0;
    static int[][] c;
    static int[][] totalSupply;
    static int[][] p;
    static int[] u;
    static int[] v;

    // main function
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("1- Kuzey Batı Köşe Kuralı ile otomatik optimumluk kontrolü \n2- Atanan Değerler ile optimumluk kontrolü \nSeçiminizi giriniz : ");
        int choice =  sc.nextInt();

        System.out.println("Satır sayısını giriniz : ");
        row = sc.nextInt();
        System.out.println("Sütun sayısını giriniz : ");
        col = sc.nextInt();
        c = new int[row][col];
        u = new int[row];
        v = new int[col];
        totalSupply = new int[row][col];
        System.out.println("Maliyetleri giriniz : ");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                c[i][j] = sc.nextInt();
            }
        }
        int[] supply = new int[row];
        int[] demand = new int[col];
        if (choice == 1){
            System.out.println("Arz miktarlarını giriniz : ");
            for (int i = 0; i < row; i++) {
                supply[i] = sc.nextInt();
            }
            System.out.println("Talep miktarlarını giriniz : ");
            for (int i = 0; i < col; i++) {
                demand[i] = sc.nextInt();
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (supply[i] == 0 || demand[j] == 0) {
                        totalSupply[i][j] = 0;
                    } else if (supply[i] > demand[j]) {

                        supply[i] = supply[i] - demand[j];
                        totalSupply[i][j] = demand[j];
                        demand[j] = 0;
                    } else {
                        demand[j] = demand[j] - supply[i];
                        totalSupply[i][j] = supply[i];
                        supply[i] = 0;
                    }
                }
            }
        }

        if (choice == 2){
            System.out.println("Atanan değerleri giriniz (Eğer hücreye atanan değer yoksa 0 giriniz.): ");
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    totalSupply[i][j] = sc.nextInt();
                }
            }
        }

        calculate();
        boolean status  = pij();
        isOptimum();

    }

    // Ux ve Vx değerlerini hesaplıyor.
    static void calculate(){
        ArrayList<Boolean> check = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                v[j] = Integer.MAX_VALUE;
                check.add(false);
            }
            u[i]=Integer.MAX_VALUE;
        }
        u[0]=0;
        do{
            count=0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if(totalSupply[i][j]!=0){
                        if(!check.get(count)){
                            if(v[j]!=Integer.MAX_VALUE&&u[i] == Integer.MAX_VALUE){
                                u[i] = c[i][j] - v[j];
                                check.remove(count);
                                check.add(count, true);
                            }else if(u[i]!=Integer.MAX_VALUE&&v[j]==Integer.MAX_VALUE){
                                v[j] = c[i][j] - u[i];
                                check.remove(count);
                                check.add(count, true);
                            }
                        }
                    }else{
                        check.remove(count);
                        check.add(count, true);
                    }
                    count++;
                }
            }
        }while(check.contains(false));
    }

    // İç indis hesaplaması yapar.
    static boolean pij(){
        p = new int[row][col];
        boolean status = false;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(totalSupply[i][j]==0){
                    p[i][j]  = c[i][j] - (u[i] + v[j]) ;
                    if(p[i][j] > 0) status = true;
                }
                else{
                    p[i][j] = 0;
                }
            }
        }

        return status;
    }
    // finding smallest pij
    static void isOptimum(){
        boolean optimum = true;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if(totalSupply[i][j]==0){
                    if(p[i][j]<0){
                        optimum = false;
                    }

                }
            }
        }
        System.out.println(optimum ? "Girilen değerler optimumdur" : "Girilen değerler optimum değildir.");
    }


}  



