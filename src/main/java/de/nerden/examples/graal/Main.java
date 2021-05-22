package de.nerden.examples.graal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.ByteSequence;

public class Main {
  public static void main(String[] args) throws IOException {
    // see fib.ts fib.wasm
    String data =
        "AGFzbQEAAAABBgFgAX8BfwMCAQAFAwEAAAcQAgNmaWIAAAZtZW1vcnkCAAo1ATMBA39BASEBIABBAEoEQANAIABBAWsiAARAIAEgAmohAyABIQIgAyEBDAELCyABDwtBAAs=";
    // byte[] binary =
    //   Files.readAllBytes(Path.of("../fib.wasm"));
    byte[] binary = Base64.getDecoder().decode(data);
    Context.Builder contextBuilder = Context.newBuilder("wasm");
    Source.Builder sourceBuilder =
        Source.newBuilder("wasm", ByteSequence.create(binary), "example");
    Source source = sourceBuilder.build();
    Context context = contextBuilder.build();

    context.eval(source);

    Value mainFunction = context.getBindings("wasm").getMember("main").getMember("fib");
    mainFunction.execute(2);
  }
}
