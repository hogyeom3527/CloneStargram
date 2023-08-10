package EL.WebProject.Clonestagram.Repository.Interface;

public interface FileRepositoryInterface {
    String saveFileSrc(String src, String userid);

    String getFileSrc(String userid);
}
