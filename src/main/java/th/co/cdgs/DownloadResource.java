package th.co.cdgs;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

@Path("download")
@ApplicationScoped
public class DownloadResource {
    @GET
    @Path("{name}")
    public Response download(@PathParam("name") String name) throws IOException {
        final java.nio.file.Path file = Paths.get(name);
        final StreamingOutput stream = (OutputStream output) -> Files.copy(file, output);
        return Response.ok().entity(stream)
                .type(Files.probeContentType(file))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + name).build();
    }
}
