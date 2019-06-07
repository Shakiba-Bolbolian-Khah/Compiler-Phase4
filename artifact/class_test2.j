.class public class_test2
.super java/lang/Object
.method public <init>()V
.limit locals 100
.limit stack 10000
aload_0
invokespecial java/lang/Object/<init>()V
return
.end method
.method public main()I
.limit locals 100
.limit stack 10000
ldc 3
anewarray java/lang/String
astore 1
ldc 2
anewarray class_test2
astore 2
ldc 3
anewarray java/lang/String
astore 3
aload 1
arraylength
ldc 1
isub
istore 4
WHILELABEL0:
iload 4
ldc 1
ineg
if_icmpgt GTLABEL2
iconst_0
goto GTCOMPLETE3
GTLABEL2:
iconst_1
GTCOMPLETE3:
ifeq ENDWHILELABEL1
aload 1
iload 4
ldc "i"
aastore
aload 3
iload 4
ldc "j"
aastore
iload 4
iconst_1
isub
istore 4
goto WHILELABEL0
ENDWHILELABEL1:
aload 1
aload 3
invokestatic java/util/Arrays.equals([Ljava/lang/Object;[Ljava/lang/Object;)Z
ifeq ELSELABEL4
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 2
invokevirtual java/io/PrintStream/println(I)V
goto CONDITIONCOMPLETE5
ELSELABEL4:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc 3
invokevirtual java/io/PrintStream/println(I)V
CONDITIONCOMPLETE5:
ldc 0
ireturn
.end method
