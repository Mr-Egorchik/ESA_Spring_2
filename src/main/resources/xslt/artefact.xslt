<?xml version="1.0" encoding="UTF-8" ?>

<xsl:transform version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="ArrayList">
        <h1>
            Artefacts
        </h1>
        <table>
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Bonus</td>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="item">
                    <tr>
                        <td><xsl:value-of select="name"/></td>
                        <td>
                            +<xsl:value-of select="bonus/bonusValue"/> <xsl:value-of select="bonus/bonusType"/>
                        </td>
                    </tr>
                </xsl:for-each>
            </tbody>
        </table>
    </xsl:template>
</xsl:transform>