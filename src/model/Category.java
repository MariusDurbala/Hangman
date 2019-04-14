package model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private List<Word> wordList;

    public Category(String name) {
        this.name = name;
        wordList = new ArrayList<>();
    }
    public void addWordToList(Word newWord){
        wordList.add(newWord);
    }
    public int getLastIdOfWord(){
        return wordList.size()>0? wordList.get(wordList.size()-1).getId():0;
    }
}
