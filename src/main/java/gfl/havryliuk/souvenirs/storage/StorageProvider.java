package gfl.havryliuk.souvenirs.storage;

import gfl.havryliuk.souvenirs.storage.FileStorage;

public interface StorageProvider {
    FileStorage getStorage();
}
