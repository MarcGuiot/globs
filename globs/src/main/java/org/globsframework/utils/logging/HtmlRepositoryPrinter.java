package org.globsframework.utils.logging;

import org.globsframework.metamodel.Field;
import org.globsframework.metamodel.GlobType;
import org.globsframework.metamodel.fields.DateField;
import org.globsframework.metamodel.utils.GlobTypeComparator;
import org.globsframework.model.Glob;
import org.globsframework.model.GlobList;
import org.globsframework.model.GlobRepository;
import org.globsframework.utils.Dates;
import org.globsframework.utils.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.globsframework.utils.Utils.sort;

class HtmlRepositoryPrinter {

   private GlobRepository repository;
   private String title;
   private HtmlLogger logger;
   private List<GlobType> types;

   public HtmlRepositoryPrinter(String title,
                                HtmlLogger logger,
                                GlobRepository repository,
                                GlobType... selectedTypes) {
      this.title = title;
      this.logger = logger;
      this.repository = repository;
      if (selectedTypes.length == 0) {
         types = sort(repository.getTypes(), GlobTypeComparator.INSTANCE);
      }
      else {
         types = Arrays.asList(selectedTypes);
      }
   }

   public void run() {
      logger.startBlock("Snapshot" + (title != null ? " - " + title : ""));
      logger.write("<div class='tabber'>");
      try {
         for (GlobType type : types) {
            logger.write("<div class='tabbertab' title='" + type.getName() + "'>");
            printType(type);
            logger.write("</div>");
         }
      }
      finally {
         logger.write("</div>");
         logger.endBlock();
      }
   }

   private void printType(GlobType type) {
      HtmlTable table = new HtmlTable(logger);
      table.writeHeader(createHeaderRow(type));
      GlobList globs = repository.getAll(type);
      for (Glob glob : globs) {
         List<String> row = new ArrayList<String>();
         for (Field field : type.getFields()) {
            row.add(getValue(glob, field, glob.getValue(field)));
         }
         table.writeRow(row);
      }
      table.end();
   }

   private String toHtml(GlobType type) {
      return "<span class='globType'>" + type.getName() + "</span>";
   }

   private String getValue(Glob glob, Field field, Object value) {
      if (value == null) {
         return "";
      }
      return Strings.toString(value);
   }

   private List<String> createHeaderRow(GlobType type) {
      List<String> row = new ArrayList<String>();
      for (Field field : type.getFields()) {
         row.add(field.getName());
      }
      return row;
   }

}
