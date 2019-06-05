package toorla.ast.declaration.classDecs.classMembersDecs;

public enum AccessModifier {
    ACCESS_MODIFIER_PUBLIC, ACCESS_MODIFIER_PRIVATE;
    @Override
    public String toString()
    {
       return "(" + super.toString() + ")";
    }
    public String toStr() {
        if(super.equals(ACCESS_MODIFIER_PUBLIC)){
            return "public";
        }
        else{
            return "private";
        }
    }
}
