package uz.pdp.Travel_Agency_bot.repository;

import java.util.ArrayList;

public interface BaseRepository<T> {
    ArrayList<T> readFromFile();

    void writeToFile(T t);
}
