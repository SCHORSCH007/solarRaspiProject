public class readWrite {


                        //ab hier alles für singleton
    public static readWrite instance = null;

    public static readWrite getReadWriteClass()
    {
        if (instance != null){
            instance = new readWrite();
        }
        return instance;

    }
    public readWrite ()
    {}                  //bis hier alles für singleton
}
