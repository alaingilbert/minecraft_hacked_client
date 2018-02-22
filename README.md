# Initial setup

./decompile.sh  
Fix osx with:  
https://github.com/ModCoderPack/MCPBot-Issues/issues/501#issue-258128118  
in MinecraftDiscovery.py  
replace:

```
        if 'natives' in library:
            libFilename = "%s-%s-%s.jar"%(libSubdir, libVersion, substitueString(library['natives'][osKeyword]))
```

with:

```
        if 'natives' in library:
            if osKeyword not in library['natives']:
                continue
            libFilename = "%s-%s-%s.jar"%(libSubdir, libVersion, substitueString(library['natives'][osKeyword]))
```

-----

Import eclipse/Client in intellij

Cmd+; (File -> project structure)  
Modules  
Fix module sdk  
Fix paths  

Create new application:

Main class: Start  
VM options: -Djava.library.path=versions/1.12/1.12-natives  
Working directory: ...mcp940/jars

Osx:  
`java.lang.NoClassDefFoundError: ca/weblite/objc/NSObject`  
Add: `libraries/ca/weblite/java-objc-bridge/1.0.0/java-objc-bridge-1.0.0.jar` to your modules

# Release

`./recompile.sh`  
`./reobfuscate.sh`  
`cp ./jars/versions/1.12/1.12.jar ./reobf/minecraft/`  
`cd reobf/minecraft/`  
`jar uf 1.12.jar *`  
`zip -d 1.12.jar "META-INF/*"`  