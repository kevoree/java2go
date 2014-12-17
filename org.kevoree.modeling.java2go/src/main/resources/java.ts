class System {
    static out = {
        println(obj?:any) {
            console.log(obj);
        },
        print(obj:any) {
            console.log(obj);
        }
    };

    static err = {
        println(obj?:any) {
            console.log(obj);
        },
        print(obj:any) {
            console.log(obj);
        }
    };

    static arraycopy(src:Number[], srcPos:number, dest:Number[], destPos:number, numElements:number):void {
        for (var i = 0; i < numElements; i++) {
            dest[destPos + i] = src[srcPos + i];
        }
    }
}

var TSMap = Map;
var TSSet = Set;

interface Number {
    equals : (other:Number) => boolean;
    longValue() : number;
    floatValue() : number;
    intValue() : number;
    shortValue() : number;
}

Number.prototype.equals = function (other) {
    return this == other;
};

interface String {
    equals : (other:String) => boolean;
    startsWith : (other:String) => boolean;
    endsWith : (other:String) => boolean;
    matches :  (regEx:String) => boolean;
    //getBytes : () => number[];
    isEmpty : () => boolean;
}

class StringUtils {
    static copyValueOf (data:string[], offset:number, count:number) : string {
        var result : string = "";
        for(var i = offset; i < offset+count;i++) {
            result += data[i];
        }
        return result;
    }
}

String.prototype.matches = function (regEx) {
    return this.match(regEx).length > 0;
};

String.prototype.isEmpty = function () {
    return this.length == 0;
};

String.prototype.equals = function (other) {
    return this == other;
};


String.prototype.startsWith = function (other) {
    for (var i = 0; i < other.length; i++) {
        if (other.charAt(i) != this.charAt(i)) {
            return false;
        }
    }
    return true;
};

String.prototype.endsWith = function (other) {
    for (var i = other.length - 1; i >= 0; i--) {
        if (other.charAt(i) != this.charAt(i)) {
            return false;
        }
    }
    return true;
};

module java {

    export module lang {

        export class Double {
            public static parseDouble(val:string):number {
                return +val;
            }
        }

        export class Float {
            public static parseFloat(val:string):number {
                return +val;
            }
        }

        export class Integer {
            public static parseInt(val:string):number {
                return +val;
            }
        }

        export class Long {
            public static parseLong(val:string):number {
                return +val;
            }
        }

        export class Short {
            public static MIN_VALUE = -0x8000;
            public static MAX_VALUE = 0x7FFF;
            public static parseShort(val:string):number {
                return +val;
            }
        }

        export class Throwable {

            private message:string;
            private error:Error;

            constructor(message:string) {
                this.message = message;
                this.error = new Error(message);
            }

            printStackTrace() {
                console.error(this.error['stack']);
            }
        }


        export class Exception extends Throwable {}

        export class RuntimeException extends Exception {}

        export class IndexOutOfBoundsException extends Exception {}


        export class StringBuilder {

            buffer = "";

            public length = 0;

            append(val:any):StringBuilder {
                this.buffer = this.buffer + val;
                length = this.buffer.length;
                return this;
            }

            toString():string {
                return this.buffer;
            }

        }
    }

    export module util {

        export class Random {
            public nextInt(max:number):number {
                return Math.random() * max;
            }
        }

        export class Arrays {
            static fill(data:Number[], begin:number, nbElem:number, param:number):void {
                var max = begin + nbElem;
                for (var i = begin; i < max; i++) {
                    data[i] = param;
                }
            }
        }

        export class Collections {

            public static reverse<A>(p:List<A>):void {
                var temp = new List<A>();
                for (var i = 0; i < p.size(); i++) {
                    temp.add(p.get(i));
                }
                p.clear();
                for (var i = temp.size() - 1; i >= 0; i--) {
                    p.add(temp.get(i));
                }
            }

            public static sort<A>(p:List<A>):void {
                p.sort();
            }

        }

        export class Collection<T> {
            add(val:T):void {
                throw new java.lang.Exception("Abstract implementation");
            }

            addAll(vals:Collection<T>):void {
                throw new java.lang.Exception("Abstract implementation");
            }

            remove(val:T):void {
                throw new java.lang.Exception("Abstract implementation");
            }

            clear():void {
                throw new java.lang.Exception("Abstract implementation");
            }

            isEmpty():boolean {
                throw new java.lang.Exception("Abstract implementation");
            }

            size():number {
                throw new java.lang.Exception("Abstract implementation");
            }

            contains(val:T):boolean {
                throw new java.lang.Exception("Abstract implementation");
            }

            toArray(a:Array<T>):T[] {
                throw new java.lang.Exception("Abstract implementation");
            }
        }

        export class List<T> extends Collection<T> {

            sort() {
                this.internalArray = this.internalArray.sort((a, b)=> {
                    if (a == b) {
                        return 0;
                    } else {
                        if (a < b) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }
                });
            }

            private internalArray:Array<T> = [];

            addAll(vals:Collection<T>) {
                var tempArray = vals.toArray(null);
                for (var i = 0; i < tempArray.length; i++) {
                    this.internalArray.push(tempArray[i]);
                }
            }

            clear() {
                this.internalArray = [];
            }

            public poll():T {
                return this.internalArray.shift();
            }

            remove(val:T) {
                //TODO with filter
            }

            toArray(a:Array<T>):T[] {
                //TODO
                var result = new Array<T>(this.internalArray.length);
                this.internalArray.forEach((value:T, index:number, p1:T[])=> {
                    result[index] = value;
                });
                return result;
            }

            size():number {
                return this.internalArray.length;
            }

            add(val:T):void {
                this.internalArray.push(val);
            }

            get(index:number):T {
                return this.internalArray[index];
            }

            contains(val:T):boolean {
                for (var i = 0; i < this.internalArray.length; i++) {
                    if (this.internalArray[i] == val) {
                        return true;
                    }
                }
                return false;
            }

            isEmpty():boolean {
                return this.internalArray.length == 0;
            }
        }

        export class ArrayList<T> extends List<T> {

        }

        export class LinkedList<T> extends List<T> {

        }

        export class Map<K, V> {
            get(key:K):V {
                return this.internalMap.get(key);
            }

            put(key:K, value:V):void {
                this.internalMap.set(key, value);
            }

            containsKey(key:K):boolean {
                return this.internalMap.has(key);
            }

            remove(key:K):V {
                var tmp = this.internalMap.get(key);
                this.internalMap.delete(key);
                return tmp;
            }

            keySet():Set<K> {
                var result = new HashSet<K>();
                this.internalMap.forEach((value:V, index:K, p1)=> {
                    result.add(index);
                });
                return result;
            }

            isEmpty():boolean {
                return this.internalMap.size == 0;
            }

            values():Set<V> {
                var result = new HashSet<V>();
                this.internalMap.forEach((value:V, index:K, p1)=> {
                    result.add(value);
                });
                return result;
            }

            private internalMap = new TSMap<K,V>();

            clear():void {
                this.internalMap = new TSMap<K,V>();
            }

        }

        export class HashMap<K, V> extends Map<K,V> {

        }

        export class Set<T> extends Collection<T> {

            private internalSet = new TSSet<T>();

            add(val:T) {
                this.internalSet.add(val);
            }

            clear() {
                this.internalSet = new TSSet<T>();
            }

            contains(val:T):boolean {
                return this.internalSet.has(val);
            }

            addAll(vals:Collection<T>) {
                var tempArray = vals.toArray(null);
                for (var i = 0; i < tempArray.length; i++) {
                    this.internalSet.add(tempArray[i]);
                }
            }

            remove(val:T) {
                this.internalSet.delete(val);
            }

            size():number {
                return this.internalSet.size;
            }

            isEmpty():boolean {
                return this.internalSet.size == 0;
            }

            toArray(other:Array<T>):T[] {
                var result = new Array<T>(this.internalSet.size);
                var i = 0;

                this.internalSet.forEach((value:T, index:T, origin)=> {
                    result[i] = value;
                    i++;
                });
                return result;
            }
        }

        export class HashSet<T> extends Set<T> {

        }

    }

}

module org {

    export module junit {

        export class Assert {
            public static assertNotNull(p:any):void {
                if (p == null) {
                    throw "Assert Error " + p + " must not be null";
                }
            }

            public static assertNull(p:any):void {
                if (p != null) {
                    throw "Assert Error " + p + " must be null";
                }
            }

            public static assertEquals(p:any, p2:any):void {
                if(p.equals !== undefined) {
                    if(!p.equals(p2)) {
                        throw "Assert Error \n" + p + "\n must be equal to \n" + p2 + "\n";
                    }
                } else {
                    if (p != p2) {
                        throw "Assert Error \n" + p + "\n must be equal to \n" + p2 + "\n";
                    }
                }
            }

            public static assertNotEquals(p:any, p2:any):void {
                if(p.equals !== undefined) {
                    if(p.equals(p2)) {
                        throw "Assert Error \n" + p + "\n must not be equal to \n" + p2 + "\n";
                    }
                } else {
                    if (p == p2) {
                        throw "Assert Error \n" + p + "\n must not be equal to \n" + p2 + "\n";
                    }
                }
            }

            public static assertTrue(b:boolean):void {
                if (!b) {
                    throw "Assert Error " + b + " must be true";
                }
            }
        }

    }

}

