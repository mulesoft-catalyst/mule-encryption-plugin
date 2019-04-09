# Mule Encryption Plugin
Server Plugin That Adds Encryption Capabilities to MEL

This plugin contributes to Mule Expression Language (MEL) in Mule 3.x one additional 'decrypt' function, that uses encryption parameters configured globally. This encryption capabilities are built on top of standard JCE, built into the JDK.

Also, this plugin contributes a shell script 'encryption-tool.sh' that can be used to encrypt individual values from the command line to be used later on.

## Use Case
This plugin was built in order to give encryption support to Policies, that store configuration parameters in plain text on the server side, making them a challenge to use in certain industries like banking.

## Usage within Policies or mule Apps
If the plugin is configured correctly, within any mule app the `decrypt` function can be used like the following example:

```#[decrypt('encrypted-value')]```

This function works both on policies and standard mule applications.

## Limitations

This function does not intend to replace the encrypted property placeholder, if static configuration values are required, please go ahead and use that module.

## Configuration
This plugin is configured via system properties in `wrapper.conf`, All properties have default values, the following table displays the settings and default values:

| Key | Default Value | Description |
|-----|---------------|-------------|
| mule.encryption.plugin.keystore.location | `enc-keystore.jceks` | The location of the key store where the encryption key is stored. Must be JCEKS format |
| mule.encryption.plugin.keystore.password | `changeit` | This is a classic of java keystores :) |
| mule.encryption.plugin.key.alias | `enc-key` | The name of the encryption key within the keystore. |
| mule.encryption.plugin.key.password | `changeit` | The password of the key in the keystore. |
| mule.encryption.plugin.encryption.algorithm | `AES` | The name of the algorithm used to encrypt. |
| mule.encryption.plugin.encryption.blockMode | `CBC` | Block encryption mode since this is a block cipher. |
| mule.encryption.plugin.encryption.padding | `PKCS5Padding` | The type of padding used on the block cipher. |
| mule.encryption.plugin.cipher.corePoolSize | `16` | The core pool size of the `Cipher` objects pool. |
| mule.encryption.plugin.cipher.maxPoolSize | `128` | The maximum pool size of the `Cipher` objects pool. |

Considerations:
* We use JCEKS insted of plain encryption keys because:
  1. Human-generated keys are less secure.
  2. Encryption key is directly visible in wrapper.conf
  3. Keystore can be protected with filesystem access so it cannot be read by an attacker even in possession of the keystore password.
* When performance testing, the cipher object pool may become a bottleneck, therefore it can be tuned. The defaults are fair defaults that should give good performance on most scenarios.
* In order to use bigger keys, JCE extension must be installed in the JDK, otherwise you'll get an exception saying that encryption key is too long.
* The plugin provides a very basic shell script to encrypt values.

### Encrypting Values

After installed, the `encrypt-tool.sh` is located within the plugin folder. See usage example:

```
➜  mule-plugin-encryption ./encrypt-tool.sh mySecretPassword!
Mule Home: /Users/juancavallotti/mule/mule385
Simple encryption tool! 
usage: encrypt-tool <value to encrypt>
LZkG/MPjJJ4IHiPlw72wLsWmj+y51DKDAzj/qP5eqho=
```


## Generating an Encryption KEY
The easiest way is to use `keytool` provided in the JDK. If you run this within the conf/ directory you'll get a key that you can use out of the box without any additional configuration in `wrapper.conf`.

```keytool -genseckey -alias enc-key -keyalg AES -keysize 128 -keystore enc-keystore.jceks -storetype jceks```

Juy make sure to set the password of both the key and keystore to `changeit`. In production you want a better key and password :)


## Installation

The following steps are required to install this plugin. It assumes the person who will build it has a working maven setup for working with MuleSoft projects.

1. Clone The Repo.
1. Run `mvn clean package`
1. Inside the `taget` folder there will be a file named `mule-plugin-encryption.zip`
1. Copy `mule-plugin-encryption.zip` into the `plugins` directory of any mule runtime and restart it, the runtime will unzip and setup the plugin.
1. To verify the installation, check for lines like this in mule_ee.log

```
INFO  2018-03-27 10:47:44,982 [WrapperListener_start_runner] com.mulesoft.mule.plugin.manager.MulePluginManager: Registering plugin: mule-plugin-encryption
INFO  2018-03-27 10:47:44,993 [WrapperListener_start_runner] com.mulesoft.services.encryption.MuleEncryptionPlugin: Initialising Encryption Plugin...
```

Have Fun!
