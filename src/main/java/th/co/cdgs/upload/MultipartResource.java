package th.co.cdgs.upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jboss.resteasy.reactive.server.multipart.FormValue;
import org.jboss.resteasy.reactive.server.multipart.MultipartFormDataInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("upload")
@ApplicationScoped
public class MultipartResource {
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> upload(MultipartFormDataInput input) throws IOException {
        Map<String, Collection<FormValue>> map = input.getValues();
        List<Item> items = new ArrayList<>();
        for (Entry<String, Collection<FormValue>> entry : map.entrySet()) {
            for (FormValue value : entry.getValue()) {
                items.add(new Item(entry.getKey(),
                        value.isFileItem() ? value.getFileItem().getFileSize()
                                : value.getValue().length(),
                        value.getCharset(), value.getFileName(), value.isFileItem(),
                        value.getHeaders()));
                System.out.println(value.getFileItem());
                Files.createFile(Paths.get("upload" + File.separator + value.getFileName()));
            }

        }

        return items;
    }

    public static class Item {
        public final String name;
        public final long size;
        public final String charset;
        public final String fileName;
        public final boolean isFileItem;
        public final Map<String, List<String>> headers;

        public Item(String name, long size, String charset, String fileName, boolean isFileItem,
                Map<String, List<String>> headers) {
            this.name = name;
            this.size = size;
            this.charset = charset;
            this.fileName = fileName;
            this.isFileItem = isFileItem;
            this.headers = headers;
        }
    }
}
