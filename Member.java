public class Member {
    private static int counter = 1;
    private int id;
    private String name;
    private String phone;

    public Member(String name, String phone) {
        this.id = counter++;
        this.name = name;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }

    @Override
    public String toString() {
        return "Member{id=" + id + ", name='" + name + "', phone='" + phone + "'}";
    }
}