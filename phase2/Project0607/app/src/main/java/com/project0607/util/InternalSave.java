package com.project0607.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/** Internal save allows you to save typed map data to a designated game save file. */
public class InternalSave {

  /** The name of the internally saved file. */
  private String filename;

  /** The context this internal save belongs to. */
  private Context context;

  /**
   * Create an internal save object.
   *
   * @param context The context.
   */
  public InternalSave(Context context, String filename) {
    this.context = context;
    this.filename = filename;
  }

  /**
   * Save the given map to Android storage.
   *
   * @param typedMap The map to be saved.
   */
  public void saveMap(TypedMap typedMap) {
    File file = new File(context.getFilesDir(), filename);
    if (file.exists()) {
      context.deleteFile(filename);
    }

    try {
      FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
      ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
      HashMap<String, Object> map = typedMap.getHashMap();
      objectOutputStream.writeObject(map);
      objectOutputStream.close();
      outputStream.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** @return The map currently stored in Android storage. */
  public TypedMap getMap() {
    try {
      FileInputStream inputStream = context.openFileInput(filename);
      ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
      @SuppressWarnings("unchecked") HashMap<String, Object> map = (HashMap<String, Object>) objectInputStream.readObject();
      TypedMap dict = new TypedMap(map);
      objectInputStream.close();
      inputStream.close();
      return dict;
    } catch (Exception e) {
      e.printStackTrace();
      return new TypedMap();
    }
  }
}
