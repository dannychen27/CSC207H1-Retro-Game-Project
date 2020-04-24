package com.project0607.savedata;

import android.content.Context;

import com.project0607.util.TypedMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InternalSave {

  /** The name of the internally saved file. */
  private String filename;

  /** The context this inter save belongs to. */
  private Context context;

  /**
   * Initialize this InternalSave.
   *
   * @param context The context.
   */
  public InternalSave(Context context, String filename) {
    this.context = context;
    this.filename = filename;
  }

  /**
   * Save the given map to android storage.
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
      objectOutputStream.writeObject(typedMap);
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
      TypedMap dict = (TypedMap) objectInputStream.readObject();
      //System.out.println(dict);
      inputStream.close();
      return dict;
    } catch (Exception e) {
      e.printStackTrace();
      return new TypedMap();
    }
  }

  /**
   * @return true if the file exists in Android storage.
   */
  public boolean exists(){
    File file = new File(context.getFilesDir(), filename);
    return file.exists();
  }


}
