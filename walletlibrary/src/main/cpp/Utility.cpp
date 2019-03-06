

#include "include/Elastos.Wallet.Utility.h"
#include "include/Utility.h"


static jstring JNICALL nativeGenerateMnemonic(JNIEnv *env,jobject clazz, jstring jlanguage, jstring jwords){

    const char* language = env->GetStringUTFChars(jlanguage, NULL);
    const char* words = env->GetStringUTFChars(jwords, NULL);

    char* mnemonic = generateMnemonic(language, words);

    jstring ret = env->NewStringUTF(mnemonic);

    free(mnemonic);
    env->ReleaseStringUTFChars(jlanguage, language);
    env->ReleaseStringUTFChars(jwords, words);

    return ret;
}

//static jobject JNICALL nativeGetSeedFromMnemonic(JNIEnv *env,jobject clazz, jstring jlanguage, jstring jmnemonic, jstring jwords, jstring jpassword){
//
//    const char* mnemonic = env->GetStringUTFChars(jmnemonic, NULL);
//    const char* language = env->GetStringUTFChars(jlanguage, NULL);
//    const char* words = env->GetStringUTFChars(jwords, NULL);
//    const char* password = env->GetStringUTFChars(jpassword, NULL);
//
//    void* seed;
//    int seedLen = getSeedFromMnemonic(&seed, mnemonic, language, words, password);
//
//    char* privateKey = getSinglePrivateKey(seed, seedLen);
//    jstring ret = env->NewStringUTF(privateKey);
//
//    free(privateKey);
//    env->ReleaseStringUTFChars(jmnemonic, mnemonic);
//    env->ReleaseStringUTFChars(jlanguage, language);
//    env->ReleaseStringUTFChars(jwords, words);
//    env->ReleaseStringUTFChars(jpassword, password);
//
//    return *seed;
//}


static jstring JNICALL nativeGetSinglePrivateKey(JNIEnv *env,jobject clazz, jstring jlanguage, jstring jmnemonic, jstring jwords, jstring jpassword){

    const char* mnemonic = env->GetStringUTFChars(jmnemonic, NULL);
    const char* language = env->GetStringUTFChars(jlanguage, NULL);
    const char* words = env->GetStringUTFChars(jwords, NULL);
    const char* password = env->GetStringUTFChars(jpassword, NULL);

    void* seed;
    int seedLen = getSeedFromMnemonic(&seed, mnemonic, language, words, password);

    char* privateKey = getSinglePrivateKey(seed, seedLen);
    jstring ret = env->NewStringUTF(privateKey);

    free(privateKey);
    env->ReleaseStringUTFChars(jmnemonic, mnemonic);
    env->ReleaseStringUTFChars(jlanguage, language);
    env->ReleaseStringUTFChars(jwords, words);
    env->ReleaseStringUTFChars(jpassword, password);

    return ret;

}

static jstring JNICALL nativeGetSinglePublicKey(JNIEnv *env,jobject clazz, jstring jlanguage, jstring jmnemonic, jstring jwords, jstring jpassword){

    const char* mnemonic = env->GetStringUTFChars(jmnemonic, NULL);
    const char* language = env->GetStringUTFChars(jlanguage, NULL);
    const char* words = env->GetStringUTFChars(jwords, NULL);
    const char* password = env->GetStringUTFChars(jpassword, NULL);

    void* seed;
    int seedLen = getSeedFromMnemonic(&seed, mnemonic, language, words, password);

    char* publicKey = getSinglePublicKey(seed, seedLen);
    jstring ret = env->NewStringUTF(publicKey);

    free(publicKey);
    env->ReleaseStringUTFChars(jmnemonic, mnemonic);
    env->ReleaseStringUTFChars(jlanguage, language);
    env->ReleaseStringUTFChars(jwords, words);
    env->ReleaseStringUTFChars(jpassword, password);

    return ret;

}

static jstring JNICALL nativeGetDid(JNIEnv *env,jobject clazz, jstring jpublicKey){
    const char* publicKey = env->GetStringUTFChars(jpublicKey, NULL);

    char* did = getDid(publicKey);

    jstring ret = env->NewStringUTF(did);
    env->ReleaseStringUTFChars(jpublicKey, publicKey);

    return ret;
}

static jstring JNICALL nativeGetAddress(JNIEnv *env,jobject clazz, jstring jpublickey){
    const char* publickey = env->GetStringUTFChars(jpublickey, NULL);

    char* address = getAddress(publickey);
    jstring ret = env->NewStringUTF(address);

    free(address);
    env->ReleaseStringUTFChars(jpublickey, publickey);

    return ret;
}

static jstring JNICALL nativeGenerateRawTransaction(JNIEnv *env,jobject clazz, jstring jtransaction){
    const char* transaction = env->GetStringUTFChars(jtransaction, NULL);

    char* rawTransaction = generateRawTransaction(transaction);
    jstring ret = env->NewStringUTF(rawTransaction);

    free(rawTransaction);
    env->ReleaseStringUTFChars(jtransaction, transaction);

    return ret;
}


static jbyteArray JNICALL nativeSign(JNIEnv *env, jobject clazz, jstring jprivateKey, jbyteArray jdata) {
    const char* privateKey = env->GetStringUTFChars(jprivateKey, NULL);
    jbyte* data = env->GetByteArrayElements(jdata, NULL);

    void* singedData;
    int len = sign(privateKey, data, env->GetArrayLength(jdata), &singedData);

    jbyteArray jarray = env->NewByteArray(len);
    env->SetByteArrayRegion(jarray, 0, len, static_cast<const jbyte *>(singedData));

    free(singedData);
    env->ReleaseStringUTFChars(jprivateKey, privateKey);
    env->ReleaseByteArrayElements(jdata, data, 0);

    return jarray;
}

static jboolean JNICALL nativeVerify(JNIEnv *env, jobject clazz, jstring jpublicKey, jbyteArray jdata, jbyteArray jsingedData){
    const char* publicKey = env->GetStringUTFChars(jpublicKey, NULL);
    jbyte* data = env->GetByteArrayElements(jdata, NULL);
    jbyte* singedData = env->GetByteArrayElements(jsingedData, NULL);

    jsize dataLength = env->GetArrayLength(jdata);
    jsize singedLength = env->GetArrayLength(jsingedData);
    bool isValid = verify(publicKey, data, dataLength, singedData, singedLength);

    env->ReleaseStringUTFChars(jpublicKey, publicKey);
    env->ReleaseByteArrayElements(jdata, data, 0);
    env->ReleaseByteArrayElements(jsingedData, singedData, 0);

    return isValid;
}

static jstring  JNICALL nativeGetPublicKeyFromPrivateKey(JNIEnv *env, jobject clazz, jstring jprivateKey){
    const char* privateKey = env->GetStringUTFChars(jprivateKey, NULL);

    char* publicKey = getPublicKeyFromPrivateKey(privateKey);

    jstring ret = env->NewStringUTF(publicKey);

    free(publicKey);
    env->ReleaseStringUTFChars(jprivateKey, privateKey);
    return ret;
}

static jboolean JNICALL nativeIsAddressValid(JNIEnv *env, jobject clazz, jstring jaddress){
    const char* address = env->GetStringUTFChars(jaddress, NULL);

    bool isValid = isAddressValid(address);

    env->ReleaseStringUTFChars(jaddress, address);
    return isValid;
}

static const JNINativeMethod gMethods[] = {
        {"nativeGenerateMnemonic", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", (void*)nativeGenerateMnemonic},
        {"nativeGetSinglePrivateKey", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", (void*)nativeGetSinglePrivateKey},
        {"nativeGetSinglePublicKey", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", (void*)nativeGetSinglePublicKey},
        {"nativeGetAddress", "(Ljava/lang/String;)Ljava/lang/String;", (void*)nativeGetAddress},
        {"nativeGenerateRawTransaction", "(Ljava/lang/String;)Ljava/lang/String;", (void*)nativeGenerateRawTransaction},
        {"nativeSign", "(Ljava/lang/String;[B)[B", (void*)nativeSign},
        {"nativeVerify", "(Ljava/lang/String;[B[B)Z", (void*)nativeVerify},
        {"nativeGetDid", "(Ljava/lang/String;)Ljava/lang/String;",(void*)nativeGetDid},
        {"nativeGetPublicKeyFromPrivateKey", "(Ljava/lang/String;)Ljava/lang/String;",(void*)nativeGetPublicKeyFromPrivateKey},
        {"nativeIsAddressValid", "(Ljava/lang/String;)Z",(void*)nativeIsAddressValid}
};

jint register_methods(JNIEnv *env)
{
    return jniRegisterNativeMethods(env, "com/elastos/jni/Utility",
                                    gMethods, NELEM(gMethods));
}


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved){
    JNIEnv * env;
    if(vm->GetEnv((void **)&env,JNI_VERSION_1_6) != JNI_OK){
        return JNI_ERR;
    }

    register_methods(env);

    return JNI_VERSION_1_6;
}

int jniRegisterNativeMethods(JNIEnv* env, const char* className,
                             const JNINativeMethod* gMethods, int numMethods)
{
    jclass cls = env->FindClass(className);
    env->RegisterNatives(cls, gMethods, numMethods);

    return 0;
}