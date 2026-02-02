
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Libary {
    private ArrayList<Book> books = new ArrayList();
    private Scanner sc;

    public Libary() {
        this.sc = new Scanner(System.in);
    }

    public void initData() {
        this.books.add(new Book(1, "Lập trình Java cơ bản", "Nguyễn Văn A", 120000.0));
        this.books.add(new Book(2, "Lập trình Java nâng cao", "Trần Văn B", 180000.0));
        this.books.add(new Book(3, "Lập trình C++", "Lê Văn C", 150000.0));
        this.books.add(new Book(4, "Lập trình Python", "Phạm Văn D", 160000.0));
        this.books.add(new Book(5, "Cấu trúc dữ liệu & Giải thuật", "Ngô Văn E", 200000.0));
        this.books.add(new Book(6, "Lập trình Web với Spring Boot", "Hoàng Văn F", 220000.0));
        this.books.add(new Book(7, "Lập trình Android", "Đặng Văn G", 190000.0));
        this.books.add(new Book(8, "Cơ sở dữ liệu", "Bùi Văn H", 170000.0));
        this.books.add(new Book(9, "Lập trình hướng đối tượng", "Võ Văn I", 140000.0));
        this.books.add(new Book(10, "Nhập môn Công nghệ thông tin", "Đỗ Văn K", 130000.0));
    }

    public void addBook(int id, String name, String author, double price) {
        this.books.add(new Book(id, name, author, price));
        System.out.println(" Thêm sách thành công!");
    }

    public boolean deleteBook(int id) {
        boolean removed = this.books.removeIf((b) -> {
            return b.getId() == id;
        });
        System.out.println(removed ? " Xóa thành công!" : " Không tìm thấy sách!");
        return removed;
    }

    public boolean updateBook(int id, String name, String author, double price) {
        Iterator var6 = this.books.iterator();

        Book b;
        do {
            if (!var6.hasNext()) {
                System.out.println(" Không tìm thấy sách!");
                return false;
            }

            b = (Book)var6.next();
        } while(b.getId() != id);

        b.setName(name);
        b.setAuthor(author);
        b.setPrice(price);
        System.out.println(" Sửa sách thành công!");
        return true;
    }

    public void dsSach() {
        if (this.books.isEmpty()) {
            System.out.println("Danh sách sách rỗng!");
        } else {
            System.out.println("========== DANH SÁCH SÁCH ==========");
            Iterator var1 = this.books.iterator();

            while(var1.hasNext()) {
                Book b = (Book)var1.next();
                System.out.println("ID      : " + b.getId());
                System.out.println("Tên     : " + b.getName());
                System.out.println("Tác giả : " + b.getAuthor());
                System.out.println("Giá     : " + b.getPrice());
                System.out.println("----------------------------------");
            }

        }
    }

    public void sachLT() {
        boolean found = false;
        Iterator var2 = this.books.iterator();

        while(var2.hasNext()) {
            Book b = (Book)var2.next();
            if (b.getName().toLowerCase().contains("lập trình")) {
                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Không có sách Lập trình");
        }

    }

    public void findBooksByPrice(int k, double p) {
        Stream var10000 = this.books.stream().filter((b) -> {
            return b.getPrice() <= p;
        }).limit((long)k);
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        var10000.forEach(var10001::println);
    }

    public void findBooksByAuthors(Set<String> authors) {
        Stream var10000 = this.books.stream().filter((b) -> {
            return authors.contains(b.getAuthor());
        });
        PrintStream var10001 = System.out;
        Objects.requireNonNull(var10001);
        var10000.forEach(var10001::println);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Libary lib = new Libary();
        lib.initData();

        int chon;
        do {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Thêm sách");
            System.out.println("2. Xóa sách");
            System.out.println("3. Sửa sách");
            System.out.println("4. Xuất danh sách");
            System.out.println("5. Tìm sách Lập trình");
            System.out.println("6. Lấy K sách có giá ≤ P");
            System.out.println("7. Tìm sách theo tác giả");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            chon = sc.nextInt();
            sc.nextLine();
            int n;
            double price;
            String name;
            String author;
            switch (chon) {
                case 0:
                    System.out.println("Thoát chương trình");
                    break;
                case 1:
                    System.out.print("ID: ");
                    n = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tên: ");
                    name = sc.nextLine();
                    System.out.print("Tác giả: ");
                    author = sc.nextLine();
                    System.out.print("Giá: ");
                    price = sc.nextDouble();
                    lib.addBook(n, name, author, price);
                    break;
                case 2:
                    System.out.print("Nhập ID cần xóa: ");
                    lib.deleteBook(sc.nextInt());
                    break;
                case 3:
                    System.out.print("ID cần sửa: ");
                    n = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Tên mới: ");
                    name = sc.nextLine();
                    System.out.print("Tác giả mới: ");
                    author = sc.nextLine();
                    System.out.print("Giá mới: ");
                    price = sc.nextDouble();
                    lib.updateBook(n, name, author, price);
                    break;
                case 4:
                    lib.dsSach();
                    break;
                case 5:
                    lib.sachLT();
                    break;
                case 6:
                    System.out.print("K: ");
                    n = sc.nextInt();
                    System.out.print("P: ");
                    double p = sc.nextDouble();
                    lib.findBooksByPrice(n, p);
                    break;
                case 7:
                    System.out.print("Số tác giả: ");
                    n = sc.nextInt();
                    sc.nextLine();
                    Set<String> authorSet = new HashSet();

                    for(int i = 0; i < n; ++i) {
                        System.out.print("Tác giả " + (i + 1) + ": ");
                        authorSet.add(sc.nextLine());
                    }

                    lib.findBooksByAuthors(authorSet);
                    break;
                default:
                    System.out.println("Chọn sai!");
            }
        } while(chon != 0);

    }
}
