package p9Reflection.JsonSerializer.data;

public class Job {
  private final String name;
  private final String company;
  private final Address companyAddress;

  public Job(String name, String company, Address companyAddress) {
    this.name = name;
    this.company = company;
    this.companyAddress = companyAddress;
  }

  @Override
  public String toString() {
    return "Job{" +
        "name='" + name + '\'' +
        ", company='" + company + '\'' +
        ", companyAddress=" + companyAddress +
        '}';
  }
}
