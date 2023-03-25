package org.ylab.melnikov.lesson3.orgstructure;

import java.io.File;
import java.io.IOException;

/**
 * @author Nikolay Melnikov
 */
@FunctionalInterface
public interface OrgStructureParser {
    public Employee parseStructure(File csvFile) throws IOException;
}
