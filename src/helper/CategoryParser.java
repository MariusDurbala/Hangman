package helper;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryParser extends Parser {
    public List<Category> parseDirectory(String path){
        ArrayList<Category> categories = new ArrayList<>();

        for (String categoryName: Utility.listFilesWithoutExtensionsFromPath(path)){
            categories.add(new Category(categoryName));
        }

        return categories;
    }
}
